package com.mysuite.mytrade.message.api.bean.industry;

import com.mysuite.mytrade.message.api.bean.MessagingBean;

/**
 * Created by jianl on 19/05/2017.
 */
public class IndustryMessage extends MessagingBean {

    private IndustryProfilePart industryProfilePart;
    private IndustryIndexPart industryIndexPart;
    public IndustryMessage() {
        super("industry.message");
    }

    public IndustryProfilePart getIndustryProfilePart() {
        return industryProfilePart;
    }

    public void setIndustryProfilePart(IndustryProfilePart industryProfilePart) {
        this.industryProfilePart = industryProfilePart;
    }

    public IndustryIndexPart getIndustryIndexPart() {
        return industryIndexPart;
    }

    public void setIndustryIndexPart(IndustryIndexPart industryIndexPart) {
        this.industryIndexPart = industryIndexPart;
    }

    @Override
    public String toString() {
        return "IndustryMessage{" +
                "industryProfilePart=" + industryProfilePart +
                ", industryIndexPart=" + industryIndexPart +
                '}';
    }
}
