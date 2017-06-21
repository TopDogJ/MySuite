package com.mysuite.mytrade.message.api.generator.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.utility.format.NumberFormatHelper;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.security.indicator.macd.MACDIndicator;
import com.mysuite.mytrade.message.api.bean.security.average.ExponentialMovingAverage;
import com.mysuite.mytrade.message.api.bean.security.average.SimpleMovingAverage;
import com.mysuite.mytrade.message.api.generator.QuoteMessageGenerator;
import com.mysuite.mytrade.message.api.generator.average.ExponentialMovingAverageGenerator;
import com.mysuite.mytrade.message.api.generator.average.SimpleMovingAverageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianl on 29/05/2017.
 */
@Component
public class MACDIndicatorGeneratorImpl implements MACDIndicatorGenerator {

    @Autowired
    private QuoteMessageGenerator quoteMessageGenerator;
    @Autowired
    private SimpleMovingAverageGenerator simpleMovingAverageGenerator;
    @Autowired
    private ExponentialMovingAverageGenerator exponentialMovingAverageGenerator;

    @Override
    public List<MACDIndicator> generate(String filename, Integer fast, Integer slow, Integer signal) throws MessageProcessException {
        List<QuoteMessage> quoteMessageList = this.quoteMessageGenerator.generate(filename, false);
        return this.generate(quoteMessageList,fast,slow,signal);
    }

    @Override
    public List<MACDIndicator> generate(List<QuoteMessage> quoteMessageList, Integer fast, Integer slow, Integer signal) throws MessageProcessException {
        ExponentialMovingAverage macdFastEma = null;
        ExponentialMovingAverage macdSlowEma = null;
        ExponentialMovingAverage macdSignalEma = null;
        Double macdFastSlowDifferenceTotal = null;
        BigDecimal totalPriceClosedAt = BigDecimal.ZERO;

        List<MACDIndicator> result = new ArrayList<>();
        for (int i = 0; i < quoteMessageList.size(); i++) {

            MACDIndicator macdIndicator = new MACDIndicator();
            QuoteMessage quoteMessage = quoteMessageList.get(i);
            totalPriceClosedAt = totalPriceClosedAt.add(quoteMessageList.get(i).getQuotePart().getAdjustClosedAt());


            if ((i + 1) == fast) {
                macdFastEma = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(totalPriceClosedAt, fast));
                macdIndicator.setFast(macdFastEma);
            }

            if ((i + 1) > fast) {
                macdFastEma = this.assembleExponentialMovementAverage(macdFastEma.getEma(), quoteMessage.getQuotePart().getAdjustClosedAt().doubleValue(), NumberFormatHelper.formatDecimal(new Double(2) / new Double(fast + 1)));
                macdIndicator.
                        setFast(this.exponentialMovingAverageGenerator.
                                generate(quoteMessage.getQuotePart().getAdjustClosedAt(), macdFastEma.getEma(), NumberFormatHelper.formatDecimal(new Double(2) / new Double(fast + 1))));
            }

            if((i+1) < slow){
                macdIndicator.setDifference(new Double(0));
                macdIndicator.setHistogram(new Double(0));
                macdIndicator.setSignal(this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(new BigDecimal(0),slow)));
            }

            if ((i + 1) == slow) {
                macdSlowEma = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(totalPriceClosedAt, slow));
                macdIndicator.setSlow(macdSlowEma);
                macdFastSlowDifferenceTotal = macdFastEma.getEma() - macdSlowEma.getEma();
                macdIndicator.setDifference(macdFastEma.getEma() - macdSlowEma.getEma());
            }

            if ((i + 1) > slow) {
                macdSlowEma = this.exponentialMovingAverageGenerator.generate(quoteMessage.getQuotePart().getAdjustClosedAt(), macdSlowEma.getEma(), NumberFormatHelper.formatDecimal(new Double(2) / new Double(slow + 1)));
                macdIndicator.setSlow(macdSlowEma);
                macdFastSlowDifferenceTotal += macdFastEma.getEma() - macdSlowEma.getEma();
                macdIndicator.setDifference(macdFastEma.getEma() - macdSlowEma.getEma());
            }

            if((i+1) < (slow + signal)){
                macdIndicator.setHistogram(new Double(0));
                macdIndicator.setSignal(this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(new BigDecimal(0),slow)));
            }

            if ((i + 1) == (slow + signal)) {
                macdSignalEma = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(macdFastSlowDifferenceTotal,signal));
                macdIndicator.setSignal(macdSignalEma);
                macdIndicator.setHistogram((macdFastEma.getEma() - macdSlowEma.getEma()) - macdSignalEma.getEma());
            }

            if ((i + 1) > (slow + signal)) {
                macdSignalEma = this.exponentialMovingAverageGenerator.generate(macdFastEma.getEma() - macdSlowEma.getEma(), macdSignalEma.getEma(),new Double(2) / new Double(signal + 1));
                macdIndicator.setSignal(macdSignalEma);
                macdIndicator.setHistogram((macdFastEma.getEma() - macdSlowEma.getEma()) - macdSignalEma.getEma());
            }

            macdIndicator.setDate(quoteMessage.getQuotePart().getDate());
            result.add(macdIndicator);
        }
        return result;
    }

    private SimpleMovingAverage assembleSimpleMovingAverage(BigDecimal total, Integer dividedBy) {
        SimpleMovingAverage maStatisticsPart = new SimpleMovingAverage();
        maStatisticsPart.setTotal(total);
        maStatisticsPart.setDividedBy(dividedBy);
        maStatisticsPart.setSma(total.divide(new BigDecimal(dividedBy), 3, BigDecimal.ROUND_HALF_UP).doubleValue());
        return maStatisticsPart;
    }

    private ExponentialMovingAverage initialiseExponentialMovementAverage(SimpleMovingAverage sma) {
        ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage();
        exponentialMovingAverage.setEma(sma.getSma());
        return exponentialMovingAverage;
    }

    private ExponentialMovingAverage assembleExponentialMovementAverage(Double lastEma, Double currentValue, Double alpha) {
        ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage();

        Double ema = alpha * (currentValue - lastEma) + lastEma;
        exponentialMovingAverage.setEma(ema);
        exponentialMovingAverage.setAlpha(alpha);

        return exponentialMovingAverage;
    }

}
