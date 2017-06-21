package com.mysuite.mytrade.message.api.producer.vendor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.MessageProduceException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.vendor.sina.SinaVendorActionRequestMessage;
import com.mysuite.mytrade.message.api.producer.MessageProducer;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 17/05/2017.
 */
@Component
public class SinaVendorActionMessageProducer extends AbstractLoggable implements MessageProducer<SinaVendorActionRequestMessage> {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public SinaVendorActionMessageProducer() {
        super(LogFactory.getLog(SinaVendorActionMessageProducer.class));
    }

    @Override
    public void produce(SinaVendorActionRequestMessage sinaVendorActionRequestMessage) throws MessageProduceException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String data = objectMapper.writeValueAsString(sinaVendorActionRequestMessage);
            this.kafkaTemplate.send(sinaVendorActionRequestMessage.getTopic(), sinaVendorActionRequestMessage.getAction(), data);
            this.getLogger().info("Sina vendor action message produced: " + sinaVendorActionRequestMessage);
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to produce sina vendor action message: " + sinaVendorActionRequestMessage.toString());
        }
    }
}
