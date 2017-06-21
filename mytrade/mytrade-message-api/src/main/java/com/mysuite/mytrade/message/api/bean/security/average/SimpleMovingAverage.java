package com.mysuite.mytrade.message.api.bean.security.average;

import java.math.BigDecimal;

/**
 * Created by jianl on 22/05/2017.
 */
public class SimpleMovingAverage {
    private BigDecimal total;
    private Integer dividedBy;
    private Double sma;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getDividedBy() {
        return dividedBy;
    }

    public void setDividedBy(Integer dividedBy) {
        this.dividedBy = dividedBy;
    }

    public Double getSma() {
        return sma;
    }

    public void setSma(Double sma) {
        this.sma = sma;
    }

    @Override
    public String toString() {
        return "SimpleMovingAverage{" +
                "total=" + total +
                ", dividedBy=" + dividedBy +
                ", sma=" + sma +
                '}';
    }
}
