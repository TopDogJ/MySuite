package com.mysuite.mytrade.message.api.producer.industry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.MessageProduceException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;
import com.mysuite.mytrade.message.api.producer.MessageProducer;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 19/05/2017.
 */
@Component
public class IndustryMessageProducer extends AbstractLoggable implements MessageProducer<IndustryMessage> {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public IndustryMessageProducer() {
        super(LogFactory.getLog(IndustryMessageProducer.class));
    }

    @Override
    public void produce(IndustryMessage industryMessage) throws MessageProduceException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(industryMessage);
            this.kafkaTemplate.send(industryMessage.getTopic(), "INDUSTRY", data);
            this.getLogger().info("Industry message produced: " + industryMessage.toString());
        } catch (Throwable e) {
            throw new MessageProduceException("Failed to produce security message.", e);
        }
    }
}
