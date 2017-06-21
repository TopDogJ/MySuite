package com.mysuite.mytrade.message.api.bean.security;

import com.mysuite.mytrade.message.api.bean.MessagingBean;
import com.mysuite.mytrade.message.api.bean.industry.IndustryProfilePart;
import com.mysuite.mytrade.message.api.bean.security.part.PortfolioPart;
import com.mysuite.mytrade.message.api.bean.security.part.ProfilePart;

/**
 * Created by jianl on 3/04/2017.
 */
public class SecurityMessage extends MessagingBean {

    private ProfilePart profile;
    private PortfolioPart portfolio;
    private IndustryProfilePart industry;

    public SecurityMessage() {
        super("security.message");
    }

    public ProfilePart getProfile() {
        return profile;
    }

    public void setProfile(ProfilePart profile) {
        this.profile = profile;
    }

    public PortfolioPart getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(PortfolioPart portfolio) {
        this.portfolio = portfolio;
    }

    public IndustryProfilePart getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryProfilePart industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "SecurityMessage{" +
                "profile=" + profile +
                ", portfolio=" + portfolio +
                ", industry=" + industry +
                '}';
    }
}
