package com.mysuite.mytrade.archive.processor;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteFileMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;

/**
 * Created by jianl on 21/05/2017.
 */
public interface QuoteMessageProcessor {
    public String processQuoteFileMessage(final QuoteFileMessage quoteFileMessage, final String status) throws MessageProcessException;
    public QuoteMessage processQuoteMessage(final QuoteMessage quoteMessage) throws MessageProcessException;
    public void archiveQuoteMessage(final QuoteMessage quoteMessage);
}
