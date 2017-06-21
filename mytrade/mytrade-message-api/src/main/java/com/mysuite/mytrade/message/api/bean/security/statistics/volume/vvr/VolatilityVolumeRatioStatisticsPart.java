package com.mysuite.mytrade.message.api.bean.security.statistics.volume.vvr;

import java.io.Serializable;

/**
 * Created by jianl on 24/05/2017.
 */
public class VolatilityVolumeRatioStatisticsPart implements Serializable {
    private Double vr;
    private Long avs;
    private Long bvs;
    private Long cvs;
    private Integer numberOfDays;

    public Double getVr() {
        return vr;
    }

    public void setVr(Double vr) {
        this.vr = vr;
    }

    public Long getAvs() {
        return avs;
    }

    public void setAvs(Long avs) {
        this.avs = avs;
    }

    public Long getBvs() {
        return bvs;
    }

    public void setBvs(Long bvs) {
        this.bvs = bvs;
    }

    public Long getCvs() {
        return cvs;
    }

    public void setCvs(Long cvs) {
        this.cvs = cvs;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public String toString() {
        return "VolatilityVolumeRatioStatisticsPart{" +
                "vr=" + vr +
                ", avs=" + avs +
                ", bvs=" + bvs +
                ", cvs=" + cvs +
                ", numberOfDays=" + numberOfDays +
                '}';
    }
}
