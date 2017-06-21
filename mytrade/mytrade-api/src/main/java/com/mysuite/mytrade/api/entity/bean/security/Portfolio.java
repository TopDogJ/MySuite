package com.mysuite.mytrade.api.entity.bean.security;

import com.mysuite.mytrade.api.entity.bean.EntityBean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Created by jianl on 19/05/2017.
 */
@Entity
public class Portfolio extends EntityBean {
    @Basic
    private Long totalCapitalVolume;
    @Basic
    private Long totalExchangedVolume;
    @Column(precision = 20, scale = 6)
    private BigDecimal netValuePerShare;
    @Column(precision = 20, scale = 6)
    private BigDecimal lastFourQuarterTotalEPS;
    @Column(precision = 20, scale = 6)
    private BigDecimal lastYearTotalEPS;
    @Column(precision = 20, scale = 6)
    private BigDecimal lastYearNetProfit;
    @Column(precision = 20, scale = 6)
    private BigDecimal lastFourQuarterNetProfit;

    public Long getTotalCapitalVolume() {
        return totalCapitalVolume;
    }

    public void setTotalCapitalVolume(Long totalCapitalVolume) {
        this.totalCapitalVolume = totalCapitalVolume;
    }

    public Long getTotalExchangedVolume() {
        return totalExchangedVolume;
    }

    public void setTotalExchangedVolume(Long totalExchangedVolume) {
        this.totalExchangedVolume = totalExchangedVolume;
    }

    public BigDecimal getNetValuePerShare() {
        return netValuePerShare;
    }

    public void setNetValuePerShare(BigDecimal netValuePerShare) {
        this.netValuePerShare = netValuePerShare;
    }

    public BigDecimal getLastFourQuarterTotalEPS() {
        return lastFourQuarterTotalEPS;
    }

    public void setLastFourQuarterTotalEPS(BigDecimal lastFourQuarterTotalEPS) {
        this.lastFourQuarterTotalEPS = lastFourQuarterTotalEPS;
    }

    public BigDecimal getLastYearTotalEPS() {
        return lastYearTotalEPS;
    }

    public void setLastYearTotalEPS(BigDecimal lastYearTotalEPS) {
        this.lastYearTotalEPS = lastYearTotalEPS;
    }

    public BigDecimal getLastYearNetProfit() {
        return lastYearNetProfit;
    }

    public void setLastYearNetProfit(BigDecimal lastYearNetProfit) {
        this.lastYearNetProfit = lastYearNetProfit;
    }

    public BigDecimal getLastFourQuarterNetProfit() {
        return lastFourQuarterNetProfit;
    }

    public void setLastFourQuarterNetProfit(BigDecimal lastFourQuarterNetProfit) {
        this.lastFourQuarterNetProfit = lastFourQuarterNetProfit;
    }

    @Override
    protected void appendToString(StringBuffer stringBuffer) {

    }
}
