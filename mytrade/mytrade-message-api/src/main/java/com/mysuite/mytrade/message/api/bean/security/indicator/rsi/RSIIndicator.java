package com.mysuite.mytrade.message.api.bean.security.indicator.rsi;

import com.mysuite.mytrade.message.api.bean.security.fact.RelativeStrengthIndexFact;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RelativeStrengthIndex;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jianl on 28/05/2017.
 */
public class RSIIndicator implements Serializable {
    private String date;
    private RelativeStrengthIndex fastRSI;
    private RelativeStrengthIndex mediumRSI;
    private RelativeStrengthIndex slowRSI;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RelativeStrengthIndex getFastRSI() {
        return fastRSI;
    }

    public void setFastRSI(RelativeStrengthIndex fastRSI) {
        this.fastRSI = fastRSI;
    }

    public RelativeStrengthIndex getMediumRSI() {
        return mediumRSI;
    }

    public void setMediumRSI(RelativeStrengthIndex mediumRSI) {
        this.mediumRSI = mediumRSI;
    }

    public RelativeStrengthIndex getSlowRSI() {
        return slowRSI;
    }

    public void setSlowRSI(RelativeStrengthIndex slowRSI) {
        this.slowRSI = slowRSI;
    }

    @Override
    public String toString() {
        return "RSIIndicator{" +
                "date='" + date + '\'' +
                ", fastRSI=" + fastRSI +
                ", mediumRSI=" + mediumRSI +
                ", slowRSI=" + slowRSI +
                '}';
    }
}
