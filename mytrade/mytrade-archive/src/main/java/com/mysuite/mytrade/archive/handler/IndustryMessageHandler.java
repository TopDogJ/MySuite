package com.mysuite.mytrade.archive.handler;

import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.archive.processor.IndustryMessageProcessor;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;
import com.mysuite.mytrade.message.api.handler.MessageHandler;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 19/05/2017.
 */
@Component
public class IndustryMessageHandler extends AbstractLoggable implements MessageHandler<IndustryMessage> {

    @Autowired
    private IndustryMessageProcessor industryMessageProcessor;

    public IndustryMessageHandler() {
        super(LogFactory.getLog(IndustryMessageHandler.class));
    }

    @Override
    public void handleMessage(IndustryMessage industryMessage) {
        try {
            this.industryMessageProcessor.processMessage(industryMessage, true);
        } catch (Throwable e) {
            this.getLogger().fatal("Faild to process industry message:" + industryMessage, e);
        }
    }
}
