package com.mysuite.mytrade.message.api.sinker;

import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.type.FileType;

/**
 * Created by jianl on 21/05/2017.
 */
public interface QuoteMessageFileSinker {
    public void sinkToFile(QuoteMessage quoteMessage, FileType fileType, String fileToSink);
}
