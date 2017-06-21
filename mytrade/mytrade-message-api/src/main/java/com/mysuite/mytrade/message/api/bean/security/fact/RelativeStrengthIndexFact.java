package com.mysuite.mytrade.message.api.bean.security.fact;

import java.io.Serializable;

/**
 * Created by jianl on 28/05/2017.
 */
public class RelativeStrengthIndexFact implements Serializable {
    private Double lowest;
    private Double highest;

    public Double getLowest() {
        return lowest;
    }

    public void setLowest(Double lowest) {
        this.lowest = lowest;
    }

    public Double getHighest() {
        return highest;
    }

    public void setHighest(Double highest) {
        this.highest = highest;
    }

    @Override
    public String toString() {
        return "RSIFactsPart{" +
                "lowest=" + lowest +
                ", highest=" + highest +
                '}';
    }
}
