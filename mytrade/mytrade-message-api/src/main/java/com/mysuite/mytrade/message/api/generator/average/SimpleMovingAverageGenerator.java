package com.mysuite.mytrade.message.api.generator.average;

import com.mysuite.mytrade.message.api.bean.security.average.SimpleMovingAverage;

import java.math.BigDecimal;

/**
 * Created by jianl on 11/06/2017.
 */
public interface SimpleMovingAverageGenerator {
    public SimpleMovingAverage generate(BigDecimal total, Integer dividedBy);
    public SimpleMovingAverage generate(Double total, Integer dividedBy);
}

