package com.mysuite.mytrade.archive.generator.statistics;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.archive.generator.SecurityMessageGenerator;
import com.mysuite.mytrade.message.api.bean.security.indicator.mfi.MoneyFlowIndicator;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RSIIndicator;
import com.mysuite.mytrade.message.api.bean.type.FileType;
import com.mysuite.mytrade.message.api.generator.indicator.RSIIndicatorGenerator;
import com.mysuite.mytrade.message.api.sinker.QuoteMessageFileSinker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * Created by jianl on 29/05/2017.
 */
public class RSIIndicatorGeneratorImplTest extends SpringBaseTestCase {

    @Autowired
    private RSIIndicatorGenerator rsiStatisticsGenerator;
    @Autowired
    private String reduceToFileRepository;
    @Autowired
    private EntityRepository<Security> securityEntityRepository;
    @Autowired
    private SecurityMessageGenerator securityMessageGenerator;

    @Test
    public void generate() throws Exception, MessageProcessException {
        List<RSIIndicator> rsiIndicatorList = this.rsiStatisticsGenerator.generate(reduceToFileRepository + "/" + "600010" + FileType.CSV.getSuffix(), 6, 12, 24);
        rsiIndicatorList.forEach(System.out::println);
    }

    @Test
    @Transactional
    public void generateCandidate() throws EntityNotFoundException, MessageProcessException {
        List<Security> securityList = this.securityEntityRepository.findAllForReference();
        for (Security security: securityList) {
            String filename = this.reduceToFileRepository + "/" + security.getCode() + FileType.CSV.getSuffix();
            if(new File(filename).exists()) {
                List<RSIIndicator> rsiIndicatorList = this.rsiStatisticsGenerator.generate(filename, 6, 12, 24);
                for (RSIIndicator rsiIndicator: rsiIndicatorList) {
                    if (rsiIndicator.getDate().equals("2017-06-14")){
                        if(rsiIndicator.getFastRSI().getRsi().compareTo(new Double(0)) > 0 &&rsiIndicator.getFastRSI().getRsi().compareTo(new Double(40)) <= 0){
                            System.out.println(this.securityMessageGenerator.generateStandard(security));
                        }
                    }
                }
            }
        }
    }
}