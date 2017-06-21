package com.mysuite.mytrade.vendor.handler;

import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionResponseMessage;
import com.mysuite.mytrade.message.api.bean.vendor.Yahoo.YahooVendorActionMessage;
import com.mysuite.mytrade.message.api.handler.MessageHandler;
import com.mysuite.mytrade.message.api.producer.vendor.VendorActionResponseMessageProducer;
import com.mysuite.mytrade.vendor.service.YahooFinanceApiVendorService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jianl on 19/04/2017.
 */
@Component
public class YahooVendorActionMessageHandler extends AbstractLoggable implements MessageHandler<YahooVendorActionMessage> {

    @Autowired
    private YahooFinanceApiVendorService yahooFinanceApiVendorService;
    @Autowired
    private VendorActionResponseMessageProducer vendorActionResponseMessageProducer;

    public YahooVendorActionMessageHandler() {
        super(LogFactory.getLog(YahooVendorActionMessageHandler.class));
    }

    @Override
    public void handleMessage(YahooVendorActionMessage yahooVendorActionMessage) {
        try {
            switch (yahooVendorActionMessage.getAction()) {
                case "fetch.quote":
                    VendorActionResponseMessage vendorActionResponseMessage = this.yahooFinanceApiVendorService.fetchQuoteHistoryFile(yahooVendorActionMessage.getSecurityCode(), yahooVendorActionMessage.getSpecificCode());
                    this.vendorActionResponseMessageProducer.produce(vendorActionResponseMessage);
                    this.getLogger().debug("Vendor action message handled: " + yahooVendorActionMessage.toString());
                    break;
                default:
                    if (yahooVendorActionMessage.getAction() != null) {
                        this.getLogger().warn("Unexpected action <" + yahooVendorActionMessage.getAction() + ">. Action aborted.");
                    } else {
                        this.getLogger().error("Null action found event.");
                    }
                    break;
            }
        } catch (Throwable e) {
            this.getLogger().error("Failed to handle vendor action message.", e);
        }
    }
}
