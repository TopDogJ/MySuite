package com.mysuite.mytrade.message.api.generator.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.utility.format.NumberFormatHelper;
import com.mysuite.mytrade.message.api.bean.security.fact.RelativeStrengthIndexFact;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RSIIndicator;
import com.mysuite.mytrade.message.api.bean.security.average.ExponentialMovingAverage;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RelativeStrengthIndex;
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
public class RSIIndicatorGeneratorImpl implements RSIIndicatorGenerator {

    @Autowired
    private QuoteMessageGenerator quoteMessageGenerator;
    @Autowired
    private SimpleMovingAverageGenerator simpleMovingAverageGenerator;
    @Autowired
    private ExponentialMovingAverageGenerator exponentialMovingAverageGenerator;

    @Override
    public List<RSIIndicator> generate(String filename, Integer fast, Integer medium, Integer slow) throws MessageProcessException {
        List<QuoteMessage> quoteMessageList = this.quoteMessageGenerator.generate(filename, false);
        return this.generate(quoteMessageList, fast, medium, slow);
    }

    @Override
    public List<RSIIndicator> generate(List<QuoteMessage> quoteMessageList, Integer fast, Integer medium, Integer slow) throws MessageProcessException {
        ExponentialMovingAverage priceIncreasedEMAFast = null;
        ExponentialMovingAverage priceDecreasedEMAFast = null;
        ExponentialMovingAverage priceIncreasedEMAMedium = null;
        ExponentialMovingAverage priceDecreasedEMAMedium = null;
        ExponentialMovingAverage priceIncreasedEMASlow = null;
        ExponentialMovingAverage priceDecreasedEMASlow = null;


        List<RSIIndicator> resultList = new ArrayList<>();

        for (int i = 0; i < quoteMessageList.size(); i++) {
            QuoteMessage quoteMessage = quoteMessageList.get(i);

            RSIIndicator rsiIndicator = new RSIIndicator();
            RelativeStrengthIndex fastRsi = null;
            RelativeStrengthIndex mediumRsi = null;
            RelativeStrengthIndex slowRsi = null;

            if(i+1 < fast){
                fastRsi = this.assembleDefault();
            }

            if ((i + 1) == fast) {
                BigDecimal totalPriceIncreased = BigDecimal.ZERO;
                BigDecimal totalPriceDecreased = BigDecimal.ZERO;
                for (int j = i; (i + 1) - j <= fast; j--) {
                    if (quoteMessageList.get(j).getQuotePart().getPriceChange().compareTo(BigDecimal.ZERO) > 0) {
                        totalPriceIncreased = totalPriceIncreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange());

                    } else {
                        totalPriceDecreased = totalPriceDecreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange().multiply(new BigDecimal(-1)));
                    }
                }
                priceIncreasedEMAFast = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(totalPriceIncreased, fast));
                priceDecreasedEMAFast = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(totalPriceDecreased, fast));

                fastRsi = this.assembleRSI(priceIncreasedEMAFast, priceDecreasedEMAFast, fast);

            }

            if ((i + 1) > fast) {
                BigDecimal totalPriceIncreased = BigDecimal.ZERO;
                BigDecimal totalPriceDecreased = BigDecimal.ZERO;
                for (int j = i; (i + 1) - j <= fast; j--) {
                    if (quoteMessageList.get(j).getQuotePart().getPriceChange().compareTo(BigDecimal.ZERO) > 0) {
                        totalPriceIncreased = totalPriceIncreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange());
                    } else {
                        totalPriceDecreased = totalPriceDecreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange().multiply(new BigDecimal(-1)));
                    }
                }
                if (priceIncreasedEMAFast != null) {
                    priceIncreasedEMAFast = this.exponentialMovingAverageGenerator.
                            generate( totalPriceIncreased,priceIncreasedEMAFast.getEma(), NumberFormatHelper.formatDecimal(new Double(1) / new Double(fast + 1)));
                }
                if (priceDecreasedEMAFast != null) {
                    priceDecreasedEMAFast = this.exponentialMovingAverageGenerator.
                            generate(totalPriceDecreased, priceDecreasedEMAFast.getEma(), NumberFormatHelper.formatDecimal(new Double(1) / new Double(fast + 1)));
                }
                fastRsi = this.assembleRSI(priceIncreasedEMAFast, priceDecreasedEMAFast, fast);
            }

            if((i+1) < medium){
                mediumRsi = this.assembleDefault();
            }

            if ((i + 1) == medium) {
                BigDecimal totalPriceIncreased = BigDecimal.ZERO;
                BigDecimal totalPriceDecreased = BigDecimal.ZERO;
                for (int j = i; (i + 1) - j <= medium; j--) {
                    if (quoteMessageList.get(j).getQuotePart().getPriceChange().compareTo(BigDecimal.ZERO) > 0) {
                        totalPriceIncreased = totalPriceIncreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange());

                    } else {
                        totalPriceDecreased = totalPriceDecreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange().multiply(new BigDecimal(-1)));
                    }
                }
                priceIncreasedEMAMedium = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(totalPriceIncreased, medium));
                priceDecreasedEMAMedium = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(totalPriceDecreased, medium));

                mediumRsi = this.assembleRSI(priceIncreasedEMAMedium, priceDecreasedEMAMedium, medium);
            }

            if ((i + 1) > medium) {
                BigDecimal totalPriceIncreased = BigDecimal.ZERO;
                BigDecimal totalPriceDecreased = BigDecimal.ZERO;
                for (int j = i; (i + 1) - j <= medium; j--) {
                    if (quoteMessageList.get(j).getQuotePart().getPriceChange().compareTo(BigDecimal.ZERO) > 0) {
                        totalPriceIncreased = totalPriceIncreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange());
                    } else {
                        totalPriceDecreased = totalPriceDecreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange().multiply(new BigDecimal(-1)));
                    }
                }
                if (priceIncreasedEMAFast != null) {
                    priceIncreasedEMAMedium = this.exponentialMovingAverageGenerator.
                            generate(totalPriceIncreased, priceIncreasedEMAMedium.getEma(), NumberFormatHelper.formatDecimal(new Double(1) / new Double(medium + 1)));
                }
                if (priceDecreasedEMAMedium != null) {
                    priceDecreasedEMAMedium = this.exponentialMovingAverageGenerator.
                            generate(totalPriceDecreased, priceDecreasedEMAMedium.getEma(), NumberFormatHelper.formatDecimal(new Double(1) / new Double(medium + 1)));
                }
                mediumRsi = this.assembleRSI(priceIncreasedEMAMedium, priceDecreasedEMAFast, medium);
            }

            if((i+1) < slow){
                slowRsi = this.assembleDefault();
            }

            if ((i + 1) == slow) {
                BigDecimal totalPriceIncreased = BigDecimal.ZERO;
                BigDecimal totalPriceDecreased = BigDecimal.ZERO;
                for (int j = i; (i + 1) - j <= slow; j--) {
                    if (quoteMessageList.get(j).getQuotePart().getPriceChange().compareTo(BigDecimal.ZERO) > 0) {
                        totalPriceIncreased = totalPriceIncreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange());

                    } else {
                        totalPriceDecreased = totalPriceDecreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange().multiply(new BigDecimal(-1)));
                    }
                }
                priceIncreasedEMASlow = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(totalPriceIncreased, slow));
                priceDecreasedEMASlow = this.exponentialMovingAverageGenerator.initialise(this.simpleMovingAverageGenerator.generate(totalPriceDecreased, slow));
                slowRsi = this.assembleRSI(priceIncreasedEMAMedium, priceIncreasedEMAMedium, slow);
            }

            if ((i + 1) > slow) {
                BigDecimal totalPriceIncreased = BigDecimal.ZERO;
                BigDecimal totalPriceDecreased = BigDecimal.ZERO;
                for (int j = i; (i + 1) - j <= slow; j--) {
                    if (quoteMessageList.get(j).getQuotePart().getPriceChange().compareTo(BigDecimal.ZERO) > 0) {
                        totalPriceIncreased = totalPriceIncreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange());
                    } else {
                        totalPriceDecreased = totalPriceDecreased.add(quoteMessageList.get(j).getQuotePart().getPriceChange().multiply(new BigDecimal(-1)));
                    }
                }
                if (priceIncreasedEMASlow != null) {
                    priceIncreasedEMASlow = this.exponentialMovingAverageGenerator.
                            generate(totalPriceIncreased, priceIncreasedEMASlow.getEma(),NumberFormatHelper.formatDecimal(new Double(1) / new Double(slow + 1)));
                }
                if (priceDecreasedEMASlow != null) {
                    priceDecreasedEMASlow = this.exponentialMovingAverageGenerator.generate(
                            totalPriceDecreased, priceDecreasedEMASlow.getEma(), NumberFormatHelper.formatDecimal(new Double(1) / new Double(slow + 1)));
                }
                slowRsi = this.assembleRSI(priceIncreasedEMASlow, priceDecreasedEMASlow, slow);
            }

            rsiIndicator.setDate(quoteMessage.getQuotePart().getDate());
            rsiIndicator.setFastRSI(fastRsi);
            rsiIndicator.setMediumRSI(mediumRsi);
            rsiIndicator.setSlowRSI(slowRsi);

            resultList.add(rsiIndicator);
        }
        return resultList;
    }

    private RelativeStrengthIndex assembleDefault(){
        RelativeStrengthIndex rsi = new RelativeStrengthIndex();
        rsi.setRsi(new Double(0));
        return rsi;
    }

    private RelativeStrengthIndex assembleRSI(ExponentialMovingAverage priceIncreaseEma, ExponentialMovingAverage priceDecreaseEma, Integer numberOfDays) {
        RelativeStrengthIndex result = new RelativeStrengthIndex();

        result.setPriceIncreaseEma(priceIncreaseEma);
        result.setPriceDeceaseEma(priceDecreaseEma);

        if (priceIncreaseEma.getEma().compareTo(new Double(0)) == 0 || priceDecreaseEma.getEma().compareTo(new Double(0)) == 0) {
            result.setRs(new Double(0));
        } else {
            result.setRs(priceIncreaseEma.getEma() / priceDecreaseEma.getEma());
        }

        result.setRsi(new BigDecimal(100).subtract(new BigDecimal(100).divide(BigDecimal.ONE.add(new BigDecimal(result.getRs())), 2, BigDecimal.ROUND_HALF_UP)).doubleValue());
        result.setParameter(numberOfDays);

        return result;
    }
}
