package com.mysuite.mytrade.archive.processor;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;

/**
 * Created by jianl on 20/05/2017.
 */
public interface SecurityMessageProcessor {
    public void processMessage(final SecurityMessage securityMessage, final boolean checkDuplicate) throws MessageProcessException;
}
