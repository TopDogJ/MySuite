package com.mysuite.mytrade.archive.filter;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.utility.format.DateTimeFormatHelper;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.type.FileType;
import com.mysuite.mytrade.message.api.filter.QuoteMessageFilter;
import com.mysuite.mytrade.message.api.generator.QuoteMessageGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * Created by jianl on 26/05/2017.
 */
public class QuoteMessageFilterImplTest extends SpringBaseTestCase {

    @Autowired
    private QuoteMessageFilter quoteMessageFilter;
    @Autowired
    private String vendorFileRepository;
    @Autowired
    private String reduceToFileRepository;
    @Autowired
    private EntityRepository<Security> securityEntityRepository;
    @Autowired
    private QuoteMessageGenerator quoteMessageGenerator;


    @Test
    @Transactional
    public void quoteFileReduce() throws Exception, EntityNotFoundException {
        List<Security> securityList = this.securityEntityRepository.findAllForReference();
        for (Security security : securityList) {
            String vendorFilename = this.vendorFileRepository + "/" + security.getCode() + FileType.CSV.getSuffix();
            if (new File(vendorFilename).exists()) {
                String reduceToFilename = this.reduceToFileRepository + "/" + security.getCode() + FileType.CSV.getSuffix();
                try {
                    System.out.println(this.quoteMessageFilter.quoteFileReduce(security.getCode(), vendorFilename, 30, true));
                } catch (MessageProcessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void quoteMessageReduce() throws Exception, EntityNotFoundException, MessageProcessException {
//        String filename = reduceToFileRepository + "/" + "600010" + FileType.CSV.getSuffix();
//        if(new File(filename).exists()){
//            List<QuoteMessage> quoteMessageList = quoteMessageGenerator.generate(filename, false);
//            List<QuoteMessage> resultList = this.quoteMessageFilter.quoteMessageReduce(quoteMessageList, DateTimeFormatHelper.getDateFromDateString("2016-01-01"));
//            resultList.forEach(quoteMessage -> System.out.println(quoteMessage));
//        }
    }
}