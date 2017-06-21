package com.mysuite.mytrade.message.api.bean.security.statistics.bias;

import java.math.BigDecimal;

/**
 * Created by jianl on 22/05/2017.
 */
public class BiasStatisticsPart {
    private BigDecimal currentClosedAt;
    private BigDecimal pastAverageClosedAt;
    private Integer pastNumberOfDays;
    private Double bias;

    public BigDecimal getCurrentClosedAt() {
        return currentClosedAt;
    }

    public void setCurrentClosedAt(BigDecimal currentClosedAt) {
        this.currentClosedAt = currentClosedAt;
    }

    public BigDecimal getPastAverageClosedAt() {
        return pastAverageClosedAt;
    }

    public void setPastAverageClosedAt(BigDecimal pastAverageClosedAt) {
        this.pastAverageClosedAt = pastAverageClosedAt;
    }

    public Integer getPastNumberOfDays() {
        return pastNumberOfDays;
    }

    public void setPastNumberOfDays(Integer pastNumberOfDays) {
        this.pastNumberOfDays = pastNumberOfDays;
    }

    public Double getBias() {
        return bias;
    }

    public void setBias(Double bias) {
        this.bias = bias;
    }

    @Override
    public String toString() {
        return "BiasStatisticsPart{" +
                "currentClosedAt=" + currentClosedAt +
                ", pastAverageClosedAt=" + pastAverageClosedAt +
                ", pastNumberOfDays=" + pastNumberOfDays +
                ", bias=" + bias +
                '}';
    }
}
