package com.mysuite.mytrade.vendor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.VendorDataNotFoundException;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteFileMessage;
import com.mysuite.mytrade.message.api.bean.type.FileType;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionResponseMessage;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionResponseType;
import com.mysuite.mytrade.vendor.service.http.HttpService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by jianl on 28/04/2017.
 */
@Service
public class YahooFinanceApiVendorServiceImpl extends AbstractLoggable implements YahooFinanceApiVendorService {

    private static final Log logger = LogFactory.getLog(YahooFinanceApiVendorServiceImpl.class);
    private static final String YAHOO_FINANCE_API_URL = "https://query1.finance.yahoo.com/v7/finance/download/";
    private static final Long START_DATE = 66153600L;

    @Autowired
    private String vendorFileRepository;
    @Autowired
    private HttpService httpService;

    public YahooFinanceApiVendorServiceImpl() {
        super(LogFactory.getLog(YahooFinanceApiVendorServiceImpl.class));
    }

    @Override
    public VendorActionResponseMessage fetchQuoteHistoryFile(final String securityCode, final String specificCode) throws ServiceException {
        String filename = vendorFileRepository + "/" +securityCode + FileType.CSV.getSuffix();
        String code = securityCode + "." + specificCode;
        Long endDateValue = System.currentTimeMillis()/1000;
        String url = this.createUrl(code, endDateValue);
        String responseCode = "500";
        try {
            File file = new File(filename);

            if(file.exists()){
                responseCode = "200";
                this.getLogger().info("Lasted file exist: " + filename + ". No download action required.");
            }else{
                responseCode = this.httpService.doGetDownloadFile(url, filename);
            }
            return this.assembleResponseMessage(securityCode, filename, responseCode);

        } catch (Throwable e) {
            throw new ServiceException("Failed to get quote history for security <" + securityCode + ">.", e);
        }
    }

    private VendorActionResponseMessage assembleResponseMessage(String securityCode, String filename, String responseCode) throws JsonProcessingException {
        QuoteFileMessage quoteFileMessage = new QuoteFileMessage();
        quoteFileMessage.setFilename(filename);
        quoteFileMessage.setSecurityCode(securityCode);

        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(quoteFileMessage);
        VendorActionResponseMessage result = new VendorActionResponseMessage();
        result.setData(data);
        result.setStatus(responseCode);
        result.setVendorActionResponseType(VendorActionResponseType.QUOTE_FILE_MESSAGE);

        return result;
    }

    private String createUrl(String code, Long endDateValue) {
        return YAHOO_FINANCE_API_URL+code+"?"+"period1="+START_DATE+"&period2="+endDateValue+"&interval="+"1d" + "&events=" + "history" + "&crumb=" + "SUwPTjKr0SY";
    }
}
