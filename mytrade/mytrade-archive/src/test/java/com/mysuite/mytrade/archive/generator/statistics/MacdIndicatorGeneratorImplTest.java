package com.mysuite.mytrade.archive.generator.statistics;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.archive.generator.SecurityMessageGenerator;
import com.mysuite.mytrade.message.api.bean.security.indicator.macd.MACDIndicator;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RSIIndicator;
import com.mysuite.mytrade.message.api.bean.type.FileType;
import com.mysuite.mytrade.message.api.generator.indicator.MACDIndicatorGenerator;
import com.mysuite.mytrade.message.api.generator.indicator.RSIIndicatorGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jianl on 31/05/2017.
 */
public class MacdIndicatorGeneratorImplTest extends SpringBaseTestCase{

    @Autowired
    private MACDIndicatorGenerator macdStatisticsGenerator;
    @Autowired
    private String reduceToFileRepository;

    @Test
    public void generateSingle() throws MessageProcessException, EntityNotFoundException {
        List<MACDIndicator> macdIndicators = this.macdStatisticsGenerator.generate(reduceToFileRepository + "/" + "601238" + FileType.CSV.getSuffix(), 12, 24, 9);
        macdIndicators.forEach(System.out::println);
    }
}
