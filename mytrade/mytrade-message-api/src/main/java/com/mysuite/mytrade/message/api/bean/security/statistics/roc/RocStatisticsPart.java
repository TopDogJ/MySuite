package com.mysuite.mytrade.message.api.bean.security.statistics.roc;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by jianl on 22/05/2017.
 */
public class RocStatisticsPart implements Serializable {
    private BigDecimal currentClosedAt;
    private BigDecimal periodEndClosedAt;
    private Integer numberOfDays;
    private Double roc;

    public BigDecimal getCurrentClosedAt() {
        return currentClosedAt;
    }

    public void setCurrentClosedAt(BigDecimal currentClosedAt) {
        this.currentClosedAt = currentClosedAt;
    }

    public BigDecimal getPeriodEndClosedAt() {
        return periodEndClosedAt;
    }

    public void setPeriodEndClosedAt(BigDecimal periodEndClosedAt) {
        this.periodEndClosedAt = periodEndClosedAt;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Double getRoc() {
        return roc;
    }

    public void setRoc(Double roc) {
        this.roc = roc;
    }

    @Override
    public String toString() {
        return "RocStatisticsPart{" +
                "currentClosedAt=" + currentClosedAt +
                ", periodEndClosedAt=" + periodEndClosedAt +
                ", numberOfDays=" + numberOfDays +
                ", roc=" + roc +
                '}';
    }
}
