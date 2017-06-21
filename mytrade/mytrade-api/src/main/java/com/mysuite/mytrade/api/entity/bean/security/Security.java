package com.mysuite.mytrade.api.entity.bean.security;

import com.mysuite.mytrade.api.entity.bean.EntityBean;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jianl on 19/05/2017.
 */
@Entity
public class Security extends EntityBean{

    @Temporal(TemporalType.DATE)
    private Date currentQuoteDate;
    @Column(nullable = false, unique = true, length = 20)
    private String name;
    @Column(nullable = false, unique = true, length = 20)
    private String code;
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Profile profile;
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Portfolio portfolio;
    @ManyToOne
    private Status securityStatus;
    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    private Quote currentQuote;

    public Date getCurrentQuoteDate() {
        return currentQuoteDate;
    }

    public void setCurrentQuoteDate(Date currentQuoteDate) {
        this.currentQuoteDate = currentQuoteDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Status getSecurityStatus() {
        return securityStatus;
    }

    public void setSecurityStatus(Status securityStatus) {
        this.securityStatus = securityStatus;
    }

    public Quote getCurrentQuote() {
        return currentQuote;
    }

    public void setCurrentQuote(Quote currentQuote) {
        this.currentQuote = currentQuote;
    }

    @Override
    protected void appendToString(StringBuffer stringBuffer) {

    }
}
