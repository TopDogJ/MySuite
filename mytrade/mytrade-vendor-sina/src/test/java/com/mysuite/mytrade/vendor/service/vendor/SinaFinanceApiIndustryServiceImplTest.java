package com.mysuite.mytrade.vendor.service.vendor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.VendorDataNotFoundException;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by jianl on 19/05/2017.
 */
public class SinaFinanceApiIndustryServiceImplTest extends SpringBaseTestCase {

    @Autowired
    private SinaFinanceApiIndustryService sinaFinanceApiIndustryService;

    @Test
    public void createIndustryMessageByCode() throws Exception, ServiceException, VendorDataNotFoundException {
        IndustryMessage industryMessage = this.sinaFinanceApiIndustryService.createIndustryMessageByCode("hangye_ZB03");
        assertEquals("石油和天然气开采业", industryMessage.getIndustryProfilePart().getIndustryName());
    }

    @Test
    public void createAllIndustryMessage() throws Exception, ServiceException, VendorDataNotFoundException {
        List<IndustryMessage> industryMessageList = this.sinaFinanceApiIndustryService.createAllIndustryMessage();
        assertEquals(60, industryMessageList.size());
    }

    @Test
    public void createAllIndustryMessageParse() throws Exception, ServiceException, VendorDataNotFoundException {
        List<IndustryMessage> industryMessageList = this.sinaFinanceApiIndustryService.createAllIndustryMessage();
        ObjectMapper objectMapper = new ObjectMapper();
        industryMessageList.forEach(industryMessage -> {
            try {
                System.out.println(objectMapper.writeValueAsString(industryMessage));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

}