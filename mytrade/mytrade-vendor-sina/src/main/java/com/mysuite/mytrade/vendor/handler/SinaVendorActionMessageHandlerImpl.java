package com.mysuite.mytrade.vendor.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.VendorDataNotFoundException;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionRequestType;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionResponseMessage;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionResponseType;
import com.mysuite.mytrade.message.api.bean.vendor.sina.SinaVendorActionRequestMessage;
import com.mysuite.mytrade.message.api.producer.vendor.VendorActionResponseMessageProducer;
import com.mysuite.mytrade.vendor.service.vendor.SinaFinanceApiIndustryService;
import com.mysuite.mytrade.vendor.service.vendor.SinaFinanceApiQuoteService;
import com.mysuite.mytrade.vendor.service.vendor.SinaFinanceApiSecurityServiceImpl;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jianl on 18/04/2017.
 */
@Component
public class SinaVendorActionMessageHandlerImpl extends AbstractLoggable implements SinaVendorActionMessageHandler {

    @Autowired
    private SinaFinanceApiSecurityServiceImpl sinaFinanceApiVendorService;
    @Autowired
    private SinaFinanceApiIndustryService sinaFinanceApiIndustryService;
    @Autowired
    private SinaFinanceApiQuoteService sinaFinanceApiQuoteService;
    @Autowired
    private VendorActionResponseMessageProducer vendorActionResponseMessageProducer;

    public SinaVendorActionMessageHandlerImpl() {
        super(LogFactory.getLog(SinaVendorActionMessageHandlerImpl.class));
    }

    public void handleMessage(SinaVendorActionRequestMessage sinaVendorActionRequestMessage) {
        try {
            switch (sinaVendorActionRequestMessage.getAction()) {
                case "fetch.industry.all":
                    List<IndustryMessage> industryMessage = this.sinaFinanceApiIndustryService.createAllIndustryMessage();
                    this.produceIndustryMessageVendorActionResponse(industryMessage);
                    break;
                case "fetch.security.code":
                    SecurityMessage securityMessage = this.sinaFinanceApiVendorService.fetchSecurityDetailsByCode(sinaVendorActionRequestMessage.getExchangeCode(), sinaVendorActionRequestMessage.getExchangeTypeCode());
                    this.produceSecurityMessageVendorActionResponse(securityMessage);
                    this.getLogger().debug("Vendor action message handled: " + sinaVendorActionRequestMessage.toString());
                    break;
                case "fetch.security.industry":
                    List<SecurityMessage> securityMessageList = this.sinaFinanceApiVendorService.fetchSecurityDetailsByIndustry(sinaVendorActionRequestMessage.getExchangeCode(), sinaVendorActionRequestMessage.getExchangeTypeCode(), sinaVendorActionRequestMessage.getIndustryCode());
                    this.produceSecurityMessagesVendorActionResponse(securityMessageList);
                    this.getLogger().debug("Vendor action message handled: " + sinaVendorActionRequestMessage.toString());
                    break;
                case "fetch.quote":
                    QuoteMessage quoteMessage = this.sinaFinanceApiQuoteService.fetchQuote(sinaVendorActionRequestMessage.getExchangeCode(), sinaVendorActionRequestMessage.getSecurityCode());
                    this.produceQuoteMessageVendorActionResponse(quoteMessage, sinaVendorActionRequestMessage);
                    this.getLogger().debug("Vendor action message handled: " + sinaVendorActionRequestMessage.toString());
                    break;
                default:
                    if (sinaVendorActionRequestMessage.getAction() != null) {
                        this.getLogger().error("Unexpected action <" + sinaVendorActionRequestMessage.getAction() + ">. Action aborted.");
                    } else {
                        this.getLogger().error("Null action found event.");
                    }
                    break;
            }
        } catch (ServiceException e) {
            this.getLogger().error("Failed to handle vendor action event.", e);
        } catch (VendorDataNotFoundException e) {
            this.getLogger().warn("No vendor data found for vendor action message: " + sinaVendorActionRequestMessage.toString() + e.getMessage());
        }
    }

    private void produceQuoteMessageVendorActionResponse(QuoteMessage quoteMessage, SinaVendorActionRequestMessage sinaVendorActionRequestMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(quoteMessage);
            if (sinaVendorActionRequestMessage.getVendorActionRequestType().name().equals(VendorActionRequestType.ARCHIVE.name())) {
                this.assembleAndSendResponse(VendorActionResponseType.QUOTE_MESSAGE_ARCHIVE, data);
            } else {
                this.assembleAndSendResponse(VendorActionResponseType.QUOTE_MESSAGE_RT, data);
            }
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to produce vendor action reponse.", e);
        }

    }

    private void produceSecurityMessageVendorActionResponse(SecurityMessage securityMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(securityMessage);
            this.assembleAndSendResponse(VendorActionResponseType.SECURITY_MESSAGE, data);
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to produce vendor action reponse.", e);
        }
    }

    private void produceSecurityMessagesVendorActionResponse(List<SecurityMessage> securityMessageList) {
        try {
            for (SecurityMessage securityMessage : securityMessageList) {
                ObjectMapper objectMapper = new ObjectMapper();
                String data = objectMapper.writeValueAsString(securityMessage);
                this.assembleAndSendResponse(VendorActionResponseType.SECURITY_MESSAGE, data);
            }
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to produce vendor action reponse.", e);
        }
    }


    private void produceIndustryMessageVendorActionResponse(List<IndustryMessage> industryMessageList) {
        try {
            for (IndustryMessage industryMessage : industryMessageList) {
                ObjectMapper objectMapper = new ObjectMapper();
                String data = objectMapper.writeValueAsString(industryMessage);
                this.assembleAndSendResponse(VendorActionResponseType.INDUSTRY_MESSAGE, data);
            }
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to produce vendor action reponse.", e);
        }
    }

    private void assembleAndSendResponse(VendorActionResponseType vendorActionResponseType, String data) {
        VendorActionResponseMessage vendorActionResponseMessage = new VendorActionResponseMessage();
        vendorActionResponseMessage.setVendorActionResponseType(vendorActionResponseType);
        try {
            vendorActionResponseMessage.setData(data);
            vendorActionResponseMessage.setStatus("OK");
            this.produceResponse(vendorActionResponseMessage);
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to assemvle and send response.", e);
        }
    }

    private void produceResponse(VendorActionResponseMessage vendorActionResponseMessage) {
        this.vendorActionResponseMessageProducer.produce(vendorActionResponseMessage);
    }
}
