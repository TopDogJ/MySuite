package com.mysuite.mytrade.message.api.filter;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by jianl on 26/05/2017.
 */
public interface QuoteMessageFilter {
    public String quoteFileReduce(String securityCode, String originalFilename, Integer reducedByMaxNumberOfDaysGap, boolean skipHeader) throws MessageProcessException;
    public List<QuoteMessage> quoteMessageReduce(List<QuoteMessage> quoteMessageList, Date beforeDate) throws MessageProcessException;
}
