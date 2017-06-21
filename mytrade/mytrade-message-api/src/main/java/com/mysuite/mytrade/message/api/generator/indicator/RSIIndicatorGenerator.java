package com.mysuite.mytrade.message.api.generator.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RSIIndicator;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;

import java.util.List;

/**
 * Created by jianl on 29/05/2017.
 */
public interface RSIIndicatorGenerator {
    public List<RSIIndicator> generate(String filename, Integer fast, Integer medium, Integer slow) throws MessageProcessException;
    public List<RSIIndicator> generate(List<QuoteMessage> quoteMessageList, Integer fast, Integer medium, Integer slow) throws MessageProcessException;
}
