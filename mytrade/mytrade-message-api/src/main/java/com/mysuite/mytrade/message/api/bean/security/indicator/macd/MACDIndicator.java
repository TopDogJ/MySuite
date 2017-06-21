package com.mysuite.mytrade.message.api.bean.security.indicator.macd;

import com.mysuite.mytrade.message.api.bean.security.average.ExponentialMovingAverage;

import java.io.Serializable;

/**
 * Created by jianl on 27/05/2017.
 */
public class MACDIndicator implements Serializable {
    private String date;
    private ExponentialMovingAverage slow;
    private ExponentialMovingAverage fast;
    private ExponentialMovingAverage signal;
    private Double difference;
    private Double histogram;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ExponentialMovingAverage getSlow() {
        return slow;
    }

    public void setSlow(ExponentialMovingAverage slow) {
        this.slow = slow;
    }

    public ExponentialMovingAverage getFast() {
        return fast;
    }

    public void setFast(ExponentialMovingAverage fast) {
        this.fast = fast;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public ExponentialMovingAverage getSignal() {
        return signal;
    }

    public void setSignal(ExponentialMovingAverage signal) {
        this.signal = signal;
    }

    public Double getHistogram() {
        return histogram;
    }

    public void setHistogram(Double histogram) {
        this.histogram = histogram;
    }

    @Override
    public String toString() {
        return "MACDIndicator{" +
                "date='" + date + '\'' +
                ", slow=" + slow +
                ", fast=" + fast +
                ", signal=" + signal +
                ", difference=" + difference +
                ", histogram=" + histogram +
                '}';
    }
}
