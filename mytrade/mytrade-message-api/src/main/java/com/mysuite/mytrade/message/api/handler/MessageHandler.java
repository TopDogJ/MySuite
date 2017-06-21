package com.mysuite.mytrade.message.api.handler;

import com.mysuite.commons.exception.MessageProduceException;
import com.mysuite.commons.exception.process.MessageProcessException;

/**
 * Created by jianl on 27/04/2017.
 */
public interface MessageHandler<T> {
    public void handleMessage(final T t) throws MessageProduceException, MessageProcessException;
}
