package com.mysuite.mytrade.archive.processor;

import com.mysuite.commons.exception.MessageProduceException;
import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jianl on 21/05/2017.
 */
public class QuoteMessageProcessorImplTest extends SpringBaseTestCase {

    @Autowired
    private QuoteMessageProcessor quoteMessageProcessor;
    @Autowired
    private String reduceToFileRepository;
    @Autowired
    private EntityRepository<Security> securityEntityRepository;


    @Test
    public void testGenerateRSI() throws Exception, MessageProcessException, EntityNotFoundException, MessageProduceException {
//        List<QuoteMessage> quoteMessageList = this.quoteMessageGenerator.generate(this.reduceToFileRepository + "/" + "600004" +".csv");
//        List<QuoteMessage> rsiResultList = this.quoteMessageProcessor.generateRSI(quoteMessageList, 6, 12, 24);
//        rsiResultList.forEach(quoteMessage -> System.out.println(quoteMessage));
    }

    @Test
    public void testGenerateMACD() throws Exception, MessageProcessException, EntityNotFoundException, MessageProduceException {
//        List<QuoteMessage> quoteMessageList = this.quoteMessageGenerator.generate(this.reduceToFileRepository + "/" + "600004" +".csv");
//        List<QuoteMessage> macdResultList =  this.quoteMessageProcessor.generateMACD(quoteMessageList, 12, 26, 9);
//        macdResultList.forEach(quoteMessage -> System.out.println(quoteMessage));
    }
}