package com.mysuite.mytrade.message.api.generator.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.indicator.macd.MACDIndicator;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;

import java.util.List;

/**
 * Created by jianl on 29/05/2017.
 */
public interface MACDIndicatorGenerator {
    public List<MACDIndicator> generate(String filename, Integer fast, Integer slow, Integer signal) throws MessageProcessException;
    public List<MACDIndicator> generate(List<QuoteMessage> quoteMessages, Integer fast, Integer slow, Integer signal) throws MessageProcessException;
}
