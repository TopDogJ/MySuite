package com.mysuite.mytrade.appl.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.appl.handler.VendorActionResponseMessageHandler;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionResponseMessage;
import com.mysuite.mytrade.message.api.consumer.MessageConsumer;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 25/05/2017.
 */
@Component
public class VendorActionResponseConsumer extends AbstractLoggable implements MessageConsumer<ConsumerRecord<String, String>> {

    @Autowired
    private VendorActionResponseMessageHandler vendorActionResponseMessageHandler;

    public VendorActionResponseConsumer() {
        super(LogFactory.getLog(VendorActionResponseConsumer.class));
    }

    @Override
    @KafkaListener(id = "vendorActionResponseConsumer", topicPattern = "vendor.response")
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VendorActionResponseMessage vendorActionResponseMessage = objectMapper.readValue(consumerRecord.value(), VendorActionResponseMessage.class);
            this.vendorActionResponseMessageHandler.handleMessage(vendorActionResponseMessage);
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to consume vendor action response message.", e);
        }
    }
}
