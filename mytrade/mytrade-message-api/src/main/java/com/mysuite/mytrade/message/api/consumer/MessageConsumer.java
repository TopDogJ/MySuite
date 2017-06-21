package com.mysuite.mytrade.message.api.consumer;

/**
 * Created by jianl on 27/04/2017.
 */
public interface MessageConsumer<C> {
    public void consume(C consumerRecord);
}
