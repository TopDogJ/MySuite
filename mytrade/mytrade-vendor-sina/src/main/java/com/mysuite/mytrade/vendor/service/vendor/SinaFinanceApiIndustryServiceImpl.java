package com.mysuite.mytrade.vendor.service.vendor;

import com.mysuite.commons.exception.VendorDataNotFoundException;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.industry.IndustryIndexPart;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;
import com.mysuite.mytrade.message.api.bean.industry.IndustryProfilePart;
import com.mysuite.mytrade.vendor.service.http.HttpService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by jianl on 19/05/2017.
 */
@Service
public class SinaFinanceApiIndustryServiceImpl extends AbstractLoggable implements SinaFinanceApiIndustryService {

    private static final String FETCH_INDUSTRY_URL = "http://money.finance.sina.com.cn/q/view/newFLJK.php?";

    @Autowired
    private HttpService httpService;

    public SinaFinanceApiIndustryServiceImpl() {
        super(LogFactory.getLog(SinaFinanceApiIndustryServiceImpl.class));
    }


    @Override
    public IndustryMessage createIndustryMessageByCode(String industryCode) throws ServiceException, VendorDataNotFoundException {
        List<IndustryMessage> industryMessageList = this.createIndustries();
        IndustryMessage result = null;
        for (IndustryMessage industryMessage : industryMessageList) {
            if (industryMessage.getIndustryProfilePart().getVendorCode().equals(industryCode)) {
                result = industryMessage;
            }
        }
        if (result != null) {
            return result;
        } else {
            throw new VendorDataNotFoundException("Failed to create industry message by code:" + industryCode);
        }
    }

    @Override
    public List<IndustryMessage> createAllIndustryMessage() throws ServiceException, VendorDataNotFoundException {
        return this.createIndustries();
    }

    private List<IndustryMessage> createIndustries() throws ServiceException, VendorDataNotFoundException {
        Map<String, String> param = new HashMap<>();
        param.put("param=", "industry");
        String response = this.httpService.doGet(FETCH_INDUSTRY_URL, param);
        if (response == null) {
            throw new VendorDataNotFoundException("No vendor data found for industry catagory.");
        }

        try {
            return this.assembleIndustries(response);
        } catch (Throwable e) {
            throw new ServiceException("Failed to fetch industry category.", e);
        }
    }

    private List<IndustryMessage> assembleIndustries(String response) {
        String data = response.split("=")[1].trim().replace("{", "").replace("}", "").replace("\",\"", "\"|\"").replaceAll("\"", "");
        List<String> dataList = Arrays.asList(data.split("\\|"));
        List<IndustryMessage> resultList = new ArrayList<>();
        for (String message : dataList) {
            String industryCode = message.split(":")[0];
            if (industryCode.trim() != null && !(industryCode.trim().equals(""))) {
                String industryName = message.split(":")[1].split(",")[1];
                IndustryMessage industryMessage = new IndustryMessage();

                IndustryProfilePart industryProfilePart = new IndustryProfilePart();
                industryProfilePart.setVendorCode(industryCode);
                industryProfilePart.setIndustryName(industryName);
                industryMessage.setIndustryProfilePart(industryProfilePart);

                IndustryIndexPart industryIndexPart = new IndustryIndexPart();
                industryIndexPart.setNumberOfSecurities(message.split(":")[1].split(",")[2]);
                industryMessage.setIndustryIndexPart(industryIndexPart);
                resultList.add(industryMessage);
            }
        }
        return resultList;
    }

}
