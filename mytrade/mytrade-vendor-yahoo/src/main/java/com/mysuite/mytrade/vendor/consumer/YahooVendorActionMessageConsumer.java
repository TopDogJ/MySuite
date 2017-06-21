package com.mysuite.mytrade.vendor.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.vendor.Yahoo.YahooVendorActionMessage;
import com.mysuite.mytrade.message.api.consumer.MessageConsumer;
import com.mysuite.mytrade.vendor.handler.YahooVendorActionMessageHandler;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 28/04/2017.
 */
@Component
public class YahooVendorActionMessageConsumer extends AbstractLoggable implements MessageConsumer<ConsumerRecord<String, String>> {

    @Autowired
    private YahooVendorActionMessageHandler yahooVendorActionMessageHandler;

    public YahooVendorActionMessageConsumer() {
        super(LogFactory.getLog(YahooVendorActionMessageConsumer.class));
    }

    @Override
    @KafkaListener(id="yahooVendorActionMessageConsumer", topicPattern = "yahoo.vendor.action")
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            YahooVendorActionMessage yahooVendorActionMessage = objectMapper.readValue(consumerRecord.value(), YahooVendorActionMessage.class);
            this.yahooVendorActionMessageHandler.handleMessage(yahooVendorActionMessage);
            this.getLogger().debug("Vendor action message consumed: " + yahooVendorActionMessage.toString());

        } catch (Throwable e) {
            this.getLogger().error("Failed to handle vendor action.", e);
        }
    }
}
