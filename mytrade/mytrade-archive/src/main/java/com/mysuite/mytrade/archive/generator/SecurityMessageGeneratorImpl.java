package com.mysuite.mytrade.archive.generator;

import com.mysuite.mytrade.api.entity.bean.industry.Industry;
import com.mysuite.mytrade.api.entity.bean.security.Portfolio;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.message.api.bean.industry.IndustryProfilePart;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.part.PortfolioPart;
import com.mysuite.mytrade.message.api.bean.security.part.ProfilePart;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 20/05/2017.
 */
@Component
public class SecurityMessageGeneratorImpl implements SecurityMessageGenerator {

    @Override
    public SecurityMessage generateStandard(Security security) {

        ProfilePart profilePart = this.assembleProfilePart(security);
        PortfolioPart portfolioPart = this.assemblePortfolioPart(security.getPortfolio());
        IndustryProfilePart industryProfilePart = this.assembleIndustryProfile(security.getProfile().getIndustry());
        SecurityMessage result = new SecurityMessage();
        result.setProfile(profilePart);
        result.setPortfolio(portfolioPart);
        result.setIndustry(industryProfilePart);

        return result;
    }

    private IndustryProfilePart assembleIndustryProfile(Industry industry) {
        IndustryProfilePart result = new IndustryProfilePart();
        result.setVendorCode(industry.getVendorCode());
        result.setIndustryName(industry.getIndustryName());
        return result;
    }

    private PortfolioPart assemblePortfolioPart(Portfolio portfolio) {
        PortfolioPart result = new PortfolioPart();
        if (portfolio.getTotalCapitalVolume() != null) {
            result.setTotalCapitalVolume(portfolio.getTotalCapitalVolume());
        }
        if (portfolio.getTotalExchangedVolume() != null) {
            result.setTotalExchangedVolume(portfolio.getTotalExchangedVolume());
        }

        if (portfolio.getNetValuePerShare() != null) {
            result.setNetValuePerShare(portfolio.getNetValuePerShare());
        }

        if (portfolio.getLastYearNetProfit() != null) {
            result.setLastYearNetProfit(portfolio.getLastYearNetProfit());
        }

        if (portfolio.getLastFourQuarterNetProfit() != null) {
            result.setLastFourQuarterNetProfit(portfolio.getLastFourQuarterTotalEPS());
        }

        if (portfolio.getLastYearTotalEPS() != null) {
            result.setLastYearTotalEPS(portfolio.getLastYearTotalEPS());
        }

        if (portfolio.getLastFourQuarterTotalEPS() != null) {
            result.setLastFourQuarterTotalEPS(portfolio.getLastFourQuarterTotalEPS());
        }

        return result;
    }

    private ProfilePart assembleProfilePart(Security security) {
        ProfilePart result = new ProfilePart();
        result.setSecurityName(security.getName());
        result.setSecurityCode(security.getCode());
        result.setExchangeCode(security.getProfile().getExchangeType().getExchange().getCode());
        result.setExchangeTypeCode(security.getProfile().getExchangeType().getCode());
        return result;
    }
}
