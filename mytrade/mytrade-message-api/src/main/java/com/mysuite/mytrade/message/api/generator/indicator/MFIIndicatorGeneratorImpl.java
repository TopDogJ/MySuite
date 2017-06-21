package com.mysuite.mytrade.message.api.generator.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.mytrade.message.api.bean.security.average.ExponentialMovingAverage;
import com.mysuite.mytrade.message.api.bean.security.average.SimpleMovingAverage;
import com.mysuite.mytrade.message.api.bean.security.indicator.mfi.MoneyFlowIndicator;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RSIIndicator;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RelativeStrengthIndex;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.generator.QuoteMessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianl on 11/06/2017.
 */
@Component
public class MFIIndicatorGeneratorImpl implements MFIIndicatorGenerator {

    @Autowired
    private QuoteMessageGenerator quoteMessageGenerator;

    @Override
    public List<MoneyFlowIndicator> generate(String filename, Integer parameter) throws MessageProcessException {
        List<QuoteMessage> quoteMessageList = this.quoteMessageGenerator.generate(filename, false);
        return this.generate(quoteMessageList, parameter);
    }

    @Override
    public List<MoneyFlowIndicator> generate(List<QuoteMessage> quoteMessageList, Integer parameter) throws MessageProcessException {

        SimpleMovingAverage priceIncreasedEMAFast = null;
        SimpleMovingAverage priceDecreasedEMAFast = null;



        List<MoneyFlowIndicator> resultList = new ArrayList<>();

        for (int i = 0; i < quoteMessageList.size(); i++) {
            QuoteMessage quoteMessage = quoteMessageList.get(i);
            MoneyFlowIndicator moneyFlowIndicator = new MoneyFlowIndicator();

            if((i+1) < parameter){
                moneyFlowIndicator.setMfi(new Double(0));
            }

            if((i+1) >= parameter){
                BigDecimal totalPositiveMoneyFlow = BigDecimal.ZERO;
                BigDecimal totalNegativeMoneyFlow = BigDecimal.ZERO;
                for (int j = i; (i + 1) - j <= parameter; j--) {
                    BigDecimal typicalPrice = quoteMessageList.get(j).getQuotePart().getHighestAt().add(quoteMessageList.get(j).getQuotePart().getLowestAt()).add(quoteMessageList.get(j).getQuotePart().getAdjustClosedAt()).divide(new BigDecimal(3), 6, BigDecimal.ROUND_HALF_UP);
                    if (quoteMessageList.get(j).getQuotePart().getPriceChange().compareTo(BigDecimal.ZERO) > 0) {
                        totalPositiveMoneyFlow = totalPositiveMoneyFlow.add(typicalPrice.multiply(new BigDecimal(quoteMessageList.get(j).getQuotePart().getTotalVolume())));

                    } else {
                        totalNegativeMoneyFlow = totalNegativeMoneyFlow.add(typicalPrice.multiply(new BigDecimal(quoteMessageList.get(j).getQuotePart().getTotalVolume())));
                    }
                }
                moneyFlowIndicator = this.assembleMFI(totalPositiveMoneyFlow, totalNegativeMoneyFlow, parameter);
            }

            moneyFlowIndicator.setDate(quoteMessage.getQuotePart().getDate());
            resultList.add(moneyFlowIndicator);
        }
        return resultList;
    }

    private MoneyFlowIndicator assembleMFI(BigDecimal totalPositiveMoneyFlow, BigDecimal totalNegativeMoneyFlow, Integer parameter) {
        MoneyFlowIndicator result = new MoneyFlowIndicator();
        result.setParameter(parameter);
        if(totalNegativeMoneyFlow.compareTo(BigDecimal.ZERO) == 0){
            result.setMfiRatio(new Double(100));
        }else{
            result.setMfiRatio(totalPositiveMoneyFlow.divide(totalNegativeMoneyFlow, 6, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        result.setMfi(new BigDecimal(100).subtract(new BigDecimal(100).divide(BigDecimal.ONE.add(new BigDecimal(result.getMfiRatio())), 2, BigDecimal.ROUND_HALF_UP)).doubleValue());
        return result;
    }
}
