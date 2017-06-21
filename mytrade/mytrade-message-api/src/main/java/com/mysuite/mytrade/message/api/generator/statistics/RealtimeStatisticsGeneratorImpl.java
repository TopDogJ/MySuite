package com.mysuite.mytrade.message.api.generator.statistics;

import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.part.TransactionPart;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.security.statistics.RealtimeStatisticsPart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jianl on 1/06/2017.
 */
@Component
public class RealtimeStatisticsGeneratorImpl extends AbstractLoggable implements RealtimeStatisticsGenerator {

    public RealtimeStatisticsGeneratorImpl() {
        super(LogFactory.getLog(RealtimeStatisticsGeneratorImpl.class));
    }

    @Override
    public RealtimeStatisticsPart generate(QuoteMessage quoteMessage) {
        RealtimeStatisticsPart realtimeStatisticsPart = new RealtimeStatisticsPart();
        if(quoteMessage.getSecurityPortfolio().getTotalExchangedVolume() != null && quoteMessage.getSecurityPortfolio().getTotalExchangedVolume().compareTo(0L) != 0){
            if(quoteMessage.getQuotePart().getTotalVolume() != null && quoteMessage.getQuotePart().getTotalVolume().compareTo(0L) !=0){
                realtimeStatisticsPart.setExchangeVolumeRatio(this.assembleExchangeVolumeRatio(quoteMessage.getQuotePart().getTotalVolume(),
                                                              quoteMessage.getSecurityPortfolio().getTotalExchangedVolume()));
            }
        }
        if(quoteMessage.getSecurityPortfolio().getLastFourQuarterTotalEPS() != null && quoteMessage.getSecurityPortfolio().getLastYearTotalEPS().compareTo(BigDecimal.ZERO) != 0){
            if(quoteMessage.getQuotePart().getCurrentAt() != null && quoteMessage.getQuotePart().getCurrentAt().compareTo(BigDecimal.ZERO) != 0){
                realtimeStatisticsPart.setStaticPriceEarningRatio(this.assembleEarningRatio(quoteMessage.getQuotePart().getCurrentAt(), quoteMessage.getSecurityPortfolio().getLastYearTotalEPS()));
            }
        }
        if(quoteMessage.getSecurityPortfolio().getLastFourQuarterTotalEPS() != null && quoteMessage.getSecurityPortfolio().getLastFourQuarterTotalEPS().compareTo(BigDecimal.ZERO) != 0) {
            if (quoteMessage.getQuotePart().getCurrentAt() != null && quoteMessage.getQuotePart().getCurrentAt().compareTo(BigDecimal.ZERO) != 0) {
                realtimeStatisticsPart.setForwardPriceEarningRatio(this.assembleEarningRatio(quoteMessage.getQuotePart().getCurrentAt(), quoteMessage.getSecurityPortfolio().getLastFourQuarterTotalEPS()));
            }
        }
        if(quoteMessage.getSecurityPortfolio().getNetValuePerShare() != null && quoteMessage.getSecurityPortfolio().getNetValuePerShare().compareTo(BigDecimal.ZERO) != 0) {
            if (quoteMessage.getQuotePart().getCurrentAt() != null && quoteMessage.getQuotePart().getCurrentAt().compareTo(BigDecimal.ZERO) != 0) {
                realtimeStatisticsPart.setPriceToBookRatio(this.assemblePriceToBookRatio(quoteMessage.getQuotePart().getCurrentAt(), quoteMessage.getSecurityPortfolio().getNetValuePerShare()));
            }
        }
        if(quoteMessage.getQuotePart().getCurrentAt() != null && quoteMessage.getQuotePart().getCurrentAt().compareTo(BigDecimal.ZERO) != 0 && quoteMessage.getQuotePart().getLastDayClosedAt() != null && quoteMessage.getQuotePart().getLastDayClosedAt().compareTo(BigDecimal.ZERO) != 0){
            realtimeStatisticsPart.setPriceDifferenceRatio(this.assemblePriceDifferenceRatio(quoteMessage.getQuotePart().getCurrentAt(), quoteMessage.getQuotePart().getLastDayClosedAt()));
        }
        if(quoteMessage.getQuotePart().getLodgedBuys() != null && quoteMessage.getQuotePart().getLodgedBuys().size() > 0 && quoteMessage.getQuotePart().getLodgedSell() != null && quoteMessage.getQuotePart().getLodgedSell().size() > 0){
            realtimeStatisticsPart.setLodgedBuySellRatio(this.assembleLodgedBuySellRatio(quoteMessage.getQuotePart().getLodgedBuys(), quoteMessage.getQuotePart().getLodgedSell()));
        }
        if(quoteMessage.getQuotePart().getHighestAt() != null && quoteMessage.getQuotePart().getHighestAt().compareTo(BigDecimal.ZERO) != 0 && quoteMessage.getQuotePart().getLowestAt() != null && quoteMessage.getQuotePart().getLowestAt().compareTo(BigDecimal.ZERO) != 0 && quoteMessage.getQuotePart().getLastDayClosedAt()!= null && quoteMessage.getQuotePart().getLastDayClosedAt().compareTo(BigDecimal.ZERO) !=0){
            realtimeStatisticsPart.setAmplitudeRatio(this.assembleAmplitudeRatio(quoteMessage.getQuotePart().getHighestAt(), quoteMessage.getQuotePart().getLowestAt(), quoteMessage.getQuotePart().getLastDayClosedAt()));
        }
        return realtimeStatisticsPart;
    }



    private Double assembleAmplitudeRatio(BigDecimal highestAt, BigDecimal lowestAt, BigDecimal lastDayClosedAt) {
        return highestAt.subtract(lowestAt).divide(lastDayClosedAt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
    }

    private Double assemblePriceDifferenceRatio(BigDecimal currentAt, BigDecimal lastDayClosedAt) {
        return currentAt.subtract(lastDayClosedAt).divide(lastDayClosedAt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
    }

    private Double assembleLodgedBuySellRatio(List<TransactionPart> lodgedBuys, List<TransactionPart> lodgedSell) {
        Long totalLodgedBuyVolume = 0L;
        Long totalLodgedSellVolume = 0L;

        for (TransactionPart buy : lodgedBuys) {
            totalLodgedBuyVolume += buy.getVolume();
        }

        for (TransactionPart sell : lodgedSell) {
            totalLodgedSellVolume += sell.getVolume();
        }
        if (totalLodgedBuyVolume.compareTo(0L) == 0 || totalLodgedSellVolume.compareTo(0L) == 0) {
            this.getLogger().debug("Unexpected total lodged buy volume.");
            return null;
        } else {
            Long buySellDiff = totalLodgedBuyVolume - totalLodgedSellVolume;
            Long buySellTotal = totalLodgedBuyVolume + totalLodgedSellVolume;
            return new BigDecimal(buySellDiff).divide(new BigDecimal(buySellTotal), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
        }
    }

    private Double assemblePriceToBookRatio(BigDecimal priceAt, BigDecimal netValuePerShare) {
        return priceAt.divide(netValuePerShare, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Double assembleEarningRatio(BigDecimal priceAt, BigDecimal eps) {
        return priceAt.divide(eps, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Double assembleExchangeVolumeRatio(Long totalVolume, Long totalExchangedVolume) {
        return new BigDecimal(totalVolume).divide(new BigDecimal(totalExchangedVolume), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
    }
}
