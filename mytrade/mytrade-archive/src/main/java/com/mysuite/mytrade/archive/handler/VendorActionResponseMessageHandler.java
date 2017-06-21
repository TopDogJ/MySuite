package com.mysuite.mytrade.archive.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.archive.processor.IndustryMessageProcessor;
import com.mysuite.mytrade.archive.processor.QuoteMessageProcessor;
import com.mysuite.mytrade.archive.processor.SecurityMessageProcessor;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteFileMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionResponseMessage;
import com.mysuite.mytrade.message.api.handler.MessageHandler;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 25/05/2017.
 */
@Component
public class VendorActionResponseMessageHandler extends AbstractLoggable implements MessageHandler<VendorActionResponseMessage> {

    @Autowired
    private IndustryMessageProcessor industryMessageProcessor;
    @Autowired
    private SecurityMessageProcessor securityMessageProcessor;
    @Autowired
    private QuoteMessageProcessor quoteMessageProcessor;


    public VendorActionResponseMessageHandler() {
        super(LogFactory.getLog(VendorActionResponseMessageHandler.class));
    }

    @Override
    public void handleMessage(VendorActionResponseMessage vendorActionResponseMessage) throws MessageProcessException {
        switch (vendorActionResponseMessage.getVendorActionResponseType().name()) {
            case ("SECURITY_MESSAGE"):
                SecurityMessage securityMessage = this.parseDataToSecurityMessage(vendorActionResponseMessage.getData());
                this.securityMessageProcessor.processMessage(securityMessage, true);
                break;
            case ("QUOTE_MESSAGE_RT"):
                QuoteMessage quoteMessage = this.parseDataToQuoteMessage(vendorActionResponseMessage.getData());
                this.quoteMessageProcessor.processQuoteMessage(quoteMessage);
                break;
            case ("QUOTE_MESSAGE_ARCHIVE"):
                QuoteMessage archiveQuoteMessage = this.parseDataToQuoteMessage(vendorActionResponseMessage.getData());
                this.quoteMessageProcessor.archiveQuoteMessage(archiveQuoteMessage);
                break;
            case ("INDUSTRY_MESSAGE"):
                IndustryMessage industryMessage = this.parseDataToIndustryMessage(vendorActionResponseMessage.getData());
                this.industryMessageProcessor.processMessage(industryMessage, true);
                break;
            case ("QUOTE_FILE_MESSAGE"):
                QuoteFileMessage quoteFileMessage = this.parseDataToQuoteFileMessage(vendorActionResponseMessage.getData());
                String filename = this.quoteMessageProcessor.processQuoteFileMessage(quoteFileMessage, vendorActionResponseMessage.getStatus());
                if (filename != null) {

                }
                break;
            default:
                this.getLogger().warn("Unexpected vendor action response type: " + vendorActionResponseMessage.getVendorActionResponseType());
                break;
        }
    }

    private QuoteMessage parseDataToQuoteMessage(String data) throws MessageProcessException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, QuoteMessage.class);
        } catch (Throwable e) {
            throw new MessageProcessException("Failed to process message.", e);
        }
    }

    private QuoteFileMessage parseDataToQuoteFileMessage(String data) throws MessageProcessException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, QuoteFileMessage.class);
        } catch (Throwable e) {
            throw new MessageProcessException("Failed to process message.", e);
        }
    }

    private IndustryMessage parseDataToIndustryMessage(String data) throws MessageProcessException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, IndustryMessage.class);
        } catch (Throwable e) {
            throw new MessageProcessException("Failed to process message.", e);
        }
    }

    private SecurityMessage parseDataToSecurityMessage(String data) throws MessageProcessException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, SecurityMessage.class);
        } catch (Throwable e) {
            throw new MessageProcessException("Failed to process message.", e);
        }
    }
}
