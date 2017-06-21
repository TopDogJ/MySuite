package com.mysuite.mytrade.appl.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.appl.websocket.handler.SecurityQuoteWebsocketHandler;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteFileMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionResponseMessage;
import com.mysuite.mytrade.message.api.handler.MessageHandler;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;

/**
 * Created by jianl on 25/05/2017.
 */
@Component
public class VendorActionResponseMessageHandler extends AbstractLoggable implements MessageHandler<VendorActionResponseMessage> {

//    @Resource
//    private SecurityQuoteWebsocketHandler securityQuoteWebsocketHandler;

    public VendorActionResponseMessageHandler() {
        super(LogFactory.getLog(VendorActionResponseMessageHandler.class));
    }

    @Override
    public void handleMessage(VendorActionResponseMessage vendorActionResponseMessage) throws MessageProcessException {
//        switch (vendorActionResponseMessage.getVendorActionResponseType().name()) {
//            case ("QUOTE_MESSAGE_RT"):
//                QuoteMessage quoteMessage = this.parseDataToQuoteMessage(vendorActionResponseMessage.getData());
//                TextMessage textMessage = new TextMessage(vendorActionResponseMessage.getData().getBytes());
//                this.securityQuoteWebsocketHandler.broadcast(textMessage,quoteMessage.getSecurityProfile().getSecurityCode());
//                break;
//            default:
//                this.getLogger().warn("Unexpected vendor action response type: " + vendorActionResponseMessage.getVendorActionResponseType());
//                break;
//        }
    }

    private QuoteMessage parseDataToQuoteMessage(String data) throws MessageProcessException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, QuoteMessage.class);
        } catch (Throwable e) {
            throw new MessageProcessException("Failed to process message.", e);
        }
    }
}
