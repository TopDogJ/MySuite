package com.mysuite.mytrade.message.api.bean.security.quote;

import com.mysuite.mytrade.message.api.bean.MessagingBean;
import com.mysuite.mytrade.message.api.bean.industry.IndustryProfilePart;
import com.mysuite.mytrade.message.api.bean.security.part.PortfolioPart;
import com.mysuite.mytrade.message.api.bean.security.part.ProfilePart;
import com.mysuite.mytrade.message.api.bean.security.statistics.RealtimeStatisticsPart;

/**
 * Created by jianl on 3/04/2017.
 */
public class QuoteMessage extends MessagingBean {

    private Long id;
    private QuotePart quotePart;
    private RealtimeStatisticsPart realtimeStatistics;
    private PortfolioPart securityPortfolio;
    private ProfilePart securityProfile;
    private IndustryProfilePart industry;

    public QuoteMessage() {
        super("quote.message");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuotePart getQuotePart() {
        return quotePart;
    }

    public void setQuotePart(QuotePart quotePart) {
        this.quotePart = quotePart;
    }

    public RealtimeStatisticsPart getRealtimeStatistics() {
        return realtimeStatistics;
    }

    public void setRealtimeStatistics(RealtimeStatisticsPart realtimeStatistics) {
        this.realtimeStatistics = realtimeStatistics;
    }

    public PortfolioPart getSecurityPortfolio() {
        return securityPortfolio;
    }

    public void setSecurityPortfolio(PortfolioPart securityPortfolio) {
        this.securityPortfolio = securityPortfolio;
    }

    public ProfilePart getSecurityProfile() {
        return securityProfile;
    }

    public void setSecurityProfile(ProfilePart securityProfile) {
        this.securityProfile = securityProfile;
    }

    public IndustryProfilePart getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryProfilePart industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "QuoteMessage{" +
                "id=" + id +
                ", quotePart=" + quotePart +
                ", realtimeStatistics=" + realtimeStatistics +
                ", securityPortfolio=" + securityPortfolio +
                ", securityProfile=" + securityProfile +
                ", industry=" + industry +
                '}';
    }
}
