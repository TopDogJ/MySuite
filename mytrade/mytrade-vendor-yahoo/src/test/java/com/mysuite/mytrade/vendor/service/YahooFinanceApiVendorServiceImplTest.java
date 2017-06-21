package com.mysuite.mytrade.vendor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jianl on 19/05/2017.
 */
public class YahooFinanceApiVendorServiceImplTest extends SpringBaseTestCase{

    @Autowired
    private YahooFinanceApiVendorService yahooFinanceApiVendorService;

    @Test
    public void fetchQuoteHistory() throws Exception, ServiceException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(new FileInputStream("C:/Workspace/shared/ALL_SH_A_SECURITY.json"),"UTF-8"));
        String data = null;
        while ((data = bufferedReader.readLine()) != null){
            ObjectMapper objectMapper = new ObjectMapper();
            SecurityMessage securityMessage = objectMapper.readValue(data, SecurityMessage.class);
            switch (securityMessage.getProfile().getExchangeCode()){
                case ("sh"):
                    this.yahooFinanceApiVendorService.fetchQuoteHistoryFile(securityMessage.getProfile().getSecurityCode(), "SS");
                    break;
                case ("sz"):
                    this.yahooFinanceApiVendorService.fetchQuoteHistoryFile(securityMessage.getProfile().getSecurityCode(), "SZ");
                    break;
                default:
                    System.err.println("BOombnsdad");
                    break;
            }
        }
    }
}