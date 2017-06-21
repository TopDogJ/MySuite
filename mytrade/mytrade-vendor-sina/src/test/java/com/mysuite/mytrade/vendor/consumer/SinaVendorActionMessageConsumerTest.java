package com.mysuite.mytrade.vendor.consumer;

import com.mysuite.commons.exception.MessageProduceException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.message.api.producer.vendor.SinaVendorActionMessageProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jianl on 18/05/2017.
 */
public class SinaVendorActionMessageConsumerTest extends SpringBaseTestCase {

    @Autowired
    private SinaVendorActionMessageConsumer sinaVendorActionMessageConsumer;
    @Autowired
    private SinaVendorActionMessageProducer sinaVendorActionMessageProducer;

    @Test
    public void testConsume() throws InterruptedException, MessageProduceException {
//        SinaVendorActionMessage sinaVendorActionMessage = new SinaVendorActionMessage();
//        sinaVendorActionMessage.setAction("test");
//        sinaVendorActionMessage.setExchangeCode("sh");
//        for (int i = 0; i < 100; i++) {
//            sinaVendorActionMessageProducer.produce(sinaVendorActionMessage);
//            System.out.println(sinaVendorActionMessage.getAction() + sinaVendorActionMessage.getTopic() + i);
//        }
//
//        Thread.sleep(100000);
    }


}