package com.mysuite.mytrade.message.api.generator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.part.PortfolioPart;
import com.mysuite.mytrade.message.api.bean.security.part.ProfilePart;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.security.statistics.RealtimeStatisticsPart;

import java.util.List;

/**
 * Created by jianl on 29/05/2017.
 */
public interface QuoteMessageGenerator {
    public List<QuoteMessage> generate(String filename, boolean skipHeader) throws MessageProcessException;
    public List<QuoteMessage> generateLatest(String filename, Long timestamp, ProfilePart profilePart, PortfolioPart portfolioPart) throws MessageProcessException;
}
