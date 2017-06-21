package com.mysuite.mytrade.message.api.producer.vendor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.MessageProduceException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.vendor.Yahoo.YahooVendorActionMessage;
import com.mysuite.mytrade.message.api.producer.MessageProducer;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 20/05/2017.
 */
@Component
public class YahooVendorActionMessageProducer extends AbstractLoggable implements MessageProducer<YahooVendorActionMessage> {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public YahooVendorActionMessageProducer() {
        super(LogFactory.getLog(YahooVendorActionMessageProducer.class));
    }

    @Override
    public void produce(YahooVendorActionMessage yahooVendorActionMessage) throws MessageProduceException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String data = objectMapper.writeValueAsString(yahooVendorActionMessage);
            this.kafkaTemplate.send(yahooVendorActionMessage.getTopic(), yahooVendorActionMessage.getSpecificCode(), data);
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to produce sina vendor action message: " + yahooVendorActionMessage.toString());
        }
    }
}
