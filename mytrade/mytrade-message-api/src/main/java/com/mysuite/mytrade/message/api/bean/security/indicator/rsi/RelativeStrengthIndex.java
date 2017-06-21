package com.mysuite.mytrade.message.api.bean.security.indicator.rsi;

import com.mysuite.mytrade.message.api.bean.security.average.ExponentialMovingAverage;

import java.io.Serializable;

/**
 * Created by jianl on 22/05/2017.
 */
public class RelativeStrengthIndex implements Serializable {
    private Double rsi;
    private Double rs;
    private Integer parameter;
    private ExponentialMovingAverage priceIncreaseEma;
    private ExponentialMovingAverage priceDeceaseEma;

    public Double getRsi() {
        return rsi;
    }

    public void setRsi(Double rsi) {
        this.rsi = rsi;
    }

    public Double getRs() {
        return rs;
    }

    public void setRs(Double rs) {
        this.rs = rs;
    }

    public Integer getParameter() {
        return parameter;
    }

    public void setParameter(Integer parameter) {
        this.parameter = parameter;
    }

    public ExponentialMovingAverage getPriceIncreaseEma() {
        return priceIncreaseEma;
    }

    public void setPriceIncreaseEma(ExponentialMovingAverage priceIncreaseEma) {
        this.priceIncreaseEma = priceIncreaseEma;
    }

    public ExponentialMovingAverage getPriceDeceaseEma() {
        return priceDeceaseEma;
    }

    public void setPriceDeceaseEma(ExponentialMovingAverage priceDeceaseEma) {
        this.priceDeceaseEma = priceDeceaseEma;
    }

    @Override
    public String toString() {
        return "RelativeStrengthIndex{" +
                "rsi=" + rsi +
                ", parameter=" + parameter +
                '}';
    }
}
