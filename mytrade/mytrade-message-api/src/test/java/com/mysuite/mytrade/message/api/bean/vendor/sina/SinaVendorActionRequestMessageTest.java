package com.mysuite.mytrade.message.api.bean.vendor.sina;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionRequestType;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by jianl on 25/05/2017.
 */
public class SinaVendorActionRequestMessageTest extends TestCase {

    @Test
    public void testJsonSerialise() throws JsonProcessingException {
        SinaVendorActionRequestMessage sinaVendorActionRequestMessage = new SinaVendorActionRequestMessage();
        sinaVendorActionRequestMessage.setAction("Action");
        sinaVendorActionRequestMessage.setExchangeCode("exchangeCode");
        sinaVendorActionRequestMessage.setExchangeTypeCode("exchangeTypeCode");
        sinaVendorActionRequestMessage.setVendorActionRequestType(VendorActionRequestType.RT);
        sinaVendorActionRequestMessage.setIndustryCode("indsutryCode");
        sinaVendorActionRequestMessage.setSecurityCode("securityCode");
        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals("{\"action\":\"Action\",\"vendorActionRequestType\":\"RT\",\"exchangeCode\":\"exchangeCode\",\"exchangeTypeCode\":\"exchangeTypeCode\",\"securityCode\":\"securityCode\",\"industryCode\":\"indsutryCode\"}", objectMapper.writeValueAsString(sinaVendorActionRequestMessage));
    }

    @Test
    public void testJsonDeserialise() throws IOException {
        String string = "{\"action\":\"Action\",\"vendorActionRequestType\":\"RT\",\"exchangeCode\":\"exchangeCode\",\"exchangeTypeCode\":\"exchangeTypeCode\",\"securityCode\":\"securityCode\",\"industryCode\":\"indsutryCode\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        SinaVendorActionRequestMessage result = objectMapper.readValue(string, SinaVendorActionRequestMessage.class);
        assertEquals("SinaVendorActionRequestMessage{action='Action', vendorActionRequestType=RT, exchangeCode='exchangeCode', exchangeTypeCode='exchangeTypeCode', securityCode='securityCode', industryCode='indsutryCode'}", result.toString());

    }
}