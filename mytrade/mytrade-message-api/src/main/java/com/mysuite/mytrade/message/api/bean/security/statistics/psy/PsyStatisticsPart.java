package com.mysuite.mytrade.message.api.bean.security.statistics.psy;

import java.io.Serializable;

/**
 * Created by jianl on 24/05/2017.
 */
public class PsyStatisticsPart implements Serializable {
    private Double psy;
    private Integer numberOfDaysIncreased;
    private Integer numberOfDays;

    public Double getPsy() {
        return psy;
    }

    public void setPsy(Double psy) {
        this.psy = psy;
    }

    public Integer getNumberOfDaysIncreased() {
        return numberOfDaysIncreased;
    }

    public void setNumberOfDaysIncreased(Integer numberOfDaysIncreased) {
        this.numberOfDaysIncreased = numberOfDaysIncreased;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public String toString() {
        return "PsyStatisticsPart{" +
                "psy=" + psy +
                ", numberOfDaysIncreased=" + numberOfDaysIncreased +
                ", numberOfDays=" + numberOfDays +
                '}';
    }
}
