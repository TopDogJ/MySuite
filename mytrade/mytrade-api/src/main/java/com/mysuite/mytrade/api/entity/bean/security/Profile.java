package com.mysuite.mytrade.api.entity.bean.security;

import com.mysuite.mytrade.api.entity.bean.EntityBean;
import com.mysuite.mytrade.api.entity.bean.exchange.ExchangeType;
import com.mysuite.mytrade.api.entity.bean.industry.Industry;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by jianl on 19/05/2017.
 */
@Entity
public class Profile extends EntityBean {
    @ManyToOne(fetch = FetchType.LAZY)
    private Industry industry;
    @ManyToOne(fetch = FetchType.LAZY)
    private ExchangeType exchangeType;

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    @Override
    protected void appendToString(StringBuffer stringBuffer) {

    }
}
