package com.mysuite.mytrade.message.api.bean.security.average;

import java.io.Serializable;

/**
 * Created by jianl on 26/05/2017.
 */
public class ExponentialMovingAverage implements Serializable {
    private Double lastEma;
    private Double alpha;
    private Double ema;

    public Double getLastEma() {
        return lastEma;
    }

    public void setLastEma(Double lastEma) {
        this.lastEma = lastEma;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getEma() {
        return ema;
    }

    public void setEma(Double ema) {
        this.ema = ema;
    }

    @Override
    public String toString() {
        return "ExponentialMovingAverage{" +
                "lastEma=" + lastEma +
                ", alpha=" + alpha +
                ", ema=" + ema +
                '}';
    }
}
