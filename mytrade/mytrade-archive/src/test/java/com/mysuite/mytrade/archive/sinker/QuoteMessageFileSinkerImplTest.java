package com.mysuite.mytrade.archive.sinker;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.archive.processor.QuoteMessageProcessor;
import com.mysuite.mytrade.message.api.sinker.QuoteMessageFileSinker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jianl on 21/05/2017.
 */
public class QuoteMessageFileSinkerImplTest extends SpringBaseTestCase {

    @Autowired
    private QuoteMessageProcessor quoteMessageProcessor;
    @Autowired
    private QuoteMessageFileSinker quoteMessageFileSinker;
    @Autowired
    private EntityRepository<Security> securityEntityRepository;

    @Test
    @Transactional
    public void sinkToFile() throws Exception, MessageProcessException, EntityNotFoundException {
//        List<QuoteMessage> result = this.quoteMessageProcessor.loadQuoteHistoryFromFile("600732", "C:/Workspace/shared/archive/" + "600732" +".csv");
//        List<Security> securityList = this.securityEntityRepository.findAllForReference();
//        for (Security security: securityList) {
//            String filename = "C:/Workspace/shared/archive/" + security.getCode() +".csv";
//            File file = new File(filename);
//            if(file.exists()) {
//                List<QuoteMessage> result = this.quoteMessageProcessor.loadQuoteHistoryFromFile(security.getCode(), filename);
//                result.forEach(quoteMessage -> this.quoteMessageFileSinker.sinkToFile(quoteMessage, "json", security.getCode()));
//                file.delete();
//            }
//        }
    }
}