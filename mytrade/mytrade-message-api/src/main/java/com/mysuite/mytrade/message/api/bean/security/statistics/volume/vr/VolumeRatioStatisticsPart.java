package com.mysuite.mytrade.message.api.bean.security.statistics.volume.vr;

import java.math.BigDecimal;

/**
 * Created by jianl on 22/05/2017.
 */
public class VolumeRatioStatisticsPart {
    private Double ratio;
    private BigDecimal pastAveragePerMinute;
    private BigDecimal currentAveragePerMinute;
    private Integer numberOfDays;

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public BigDecimal getPastAveragePerMinute() {
        return pastAveragePerMinute;
    }

    public void setPastAveragePerMinute(BigDecimal pastAveragePerMinute) {
        this.pastAveragePerMinute = pastAveragePerMinute;
    }

    public BigDecimal getCurrentAveragePerMinute() {
        return currentAveragePerMinute;
    }

    public void setCurrentAveragePerMinute(BigDecimal currentAveragePerMinute) {
        this.currentAveragePerMinute = currentAveragePerMinute;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public String toString() {
        return "VolumeRatioStatisticsPart{" +
                "ratio=" + ratio +
                ", pastAveragePerMinute=" + pastAveragePerMinute +
                ", currentAveragePerMinute=" + currentAveragePerMinute +
                ", numberOfDays=" + numberOfDays +
                '}';
    }
}