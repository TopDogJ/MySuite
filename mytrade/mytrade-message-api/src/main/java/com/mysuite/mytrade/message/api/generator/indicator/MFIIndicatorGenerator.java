package com.mysuite.mytrade.message.api.generator.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.indicator.mfi.MoneyFlowIndicator;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RSIIndicator;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;

import java.util.List;

/**
 * Created by jianl on 29/05/2017.
 */
public interface MFIIndicatorGenerator {
    public List<MoneyFlowIndicator> generate(String filename, Integer parameter) throws MessageProcessException;
    public List<MoneyFlowIndicator> generate(List<QuoteMessage> quoteMessageList, Integer parameter) throws MessageProcessException;
}
