package com.mysuite.mytrade.message.api.generator.average;

import com.mysuite.mytrade.message.api.bean.security.average.ExponentialMovingAverage;
import com.mysuite.mytrade.message.api.bean.security.average.SimpleMovingAverage;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by jianl on 11/06/2017.
 */
@Component
public class ExponentialMovingAverageGeneratorImpl implements ExponentialMovingAverageGenerator {

    @Override
    public ExponentialMovingAverage initialise(SimpleMovingAverage simpleMovingAverage) {
        ExponentialMovingAverage result = new ExponentialMovingAverage();
        result.setEma(simpleMovingAverage.getSma());
        return result;
    }

    @Override
    public ExponentialMovingAverage generate(BigDecimal value, Double lastEma, Double alpha) {
        ExponentialMovingAverage result = new ExponentialMovingAverage();

        Double ema = alpha * (value.doubleValue() - lastEma) + lastEma;
        result.setEma(ema);
        result.setAlpha(alpha);
        result.setLastEma(lastEma);

        return result;
    }

    @Override
    public ExponentialMovingAverage generate(Double value, Double lastEma, Double alpha) {
        ExponentialMovingAverage result = new ExponentialMovingAverage();

        Double ema = alpha * (value - lastEma) + lastEma;
        result.setEma(ema);
        result.setAlpha(alpha);
        result.setLastEma(lastEma);

        return result;
    }
}
