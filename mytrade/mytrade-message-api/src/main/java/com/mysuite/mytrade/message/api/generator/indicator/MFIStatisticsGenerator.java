package com.mysuite.mytrade.message.api.generator.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;

import java.util.List;

/**
 * Created by jianl on 10/06/2017.
 */
public interface MFIStatisticsGenerator {
    public List<QuoteMessage> generate(String filename, Integer fast, Integer medium, Integer slow) throws MessageProcessException;
    public List<QuoteMessage> generate(List<QuoteMessage> quoteMessageList, Integer fast, Integer medium, Integer slow) throws MessageProcessException;
}
