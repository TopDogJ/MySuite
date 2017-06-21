package com.mysuite.mytrade.message.api.generator.statistics;

import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.security.statistics.RealtimeStatisticsPart;

/**
 * Created by jianl on 1/06/2017.
 */
public interface RealtimeStatisticsGenerator {
    public RealtimeStatisticsPart generate(QuoteMessage quoteMessage);
}
