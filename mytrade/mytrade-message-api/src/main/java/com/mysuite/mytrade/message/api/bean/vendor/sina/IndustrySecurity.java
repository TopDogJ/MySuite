package com.mysuite.mytrade.message.api.bean.vendor.sina;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by jianl on 16/05/2017.
 */
public class IndustrySecurity implements Serializable {
    private String symbol;
    private String code;
    private String name;
    private String trade;
    private String pricechange;
    private String changepercent;
    private String buy;
    private String sell;
    private String settlement;
    private String open;
    private String high;
    private String low;
    private Long volume;
    private Long amount;
    private String ticktime;
    private BigDecimal per;
    private BigDecimal pb;
    private BigDecimal mktcap;
    private BigDecimal nmc;
    private BigDecimal turnoverratio;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getPricechange() {
        return pricechange;
    }

    public void setPricechange(String pricechange) {
        this.pricechange = pricechange;
    }

    public String getChangepercent() {
        return changepercent;
    }

    public void setChangepercent(String changepercent) {
        this.changepercent = changepercent;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTicktime() {
        return ticktime;
    }

    public void setTicktime(String ticktime) {
        this.ticktime = ticktime;
    }

    public BigDecimal getPer() {
        return per;
    }

    public void setPer(BigDecimal per) {
        this.per = per;
    }

    public BigDecimal getPb() {
        return pb;
    }

    public void setPb(BigDecimal pb) {
        this.pb = pb;
    }

    public BigDecimal getMktcap() {
        return mktcap;
    }

    public void setMktcap(BigDecimal mktcap) {
        this.mktcap = mktcap;
    }

    public BigDecimal getNmc() {
        return nmc;
    }

    public void setNmc(BigDecimal nmc) {
        this.nmc = nmc;
    }

    public BigDecimal getTurnoverratio() {
        return turnoverratio;
    }

    public void setTurnoverratio(BigDecimal turnoverratio) {
        this.turnoverratio = turnoverratio;
    }

    @Override
    public String toString() {
        return "IndustrySecurity{" +
                "symbol='" + symbol + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", trade='" + trade + '\'' +
                ", pricechange='" + pricechange + '\'' +
                ", changepercent='" + changepercent + '\'' +
                ", buy='" + buy + '\'' +
                ", sell='" + sell + '\'' +
                ", settlement='" + settlement + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", volume=" + volume +
                ", amount=" + amount +
                ", ticktime='" + ticktime + '\'' +
                ", per=" + per +
                ", pb=" + pb +
                ", mktcap=" + mktcap +
                ", nmc=" + nmc +
                ", turnoverratio=" + turnoverratio +
                '}';
    }
}
