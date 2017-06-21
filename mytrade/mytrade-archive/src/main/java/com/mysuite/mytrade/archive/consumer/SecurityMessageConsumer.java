package com.mysuite.mytrade.archive.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.archive.handler.SecurityMessageHandler;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.consumer.MessageConsumer;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 20/05/2017.
 */
@Component
public class SecurityMessageConsumer extends AbstractLoggable implements MessageConsumer<ConsumerRecord<String, String>> {

    @Autowired
    private SecurityMessageHandler securityMessageHandler;

    public SecurityMessageConsumer() {
        super(LogFactory.getLog(SecurityMessageConsumer.class));
    }

    @Override
    @KafkaListener(id = "securityMessageArchiveMessageConsumer", topicPattern = "security.message")
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SecurityMessage securityMessage = objectMapper.readValue(consumerRecord.value(), SecurityMessage.class);
            this.securityMessageHandler.handleMessage(securityMessage);
        } catch (Throwable e) {
            this.getLogger().info("Failed to consume message.", e);
        }
    }
}
