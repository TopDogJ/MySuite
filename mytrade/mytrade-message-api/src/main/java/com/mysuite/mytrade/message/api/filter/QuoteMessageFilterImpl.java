package com.mysuite.mytrade.message.api.filter;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.commons.utility.format.DateTimeFormatHelper;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.type.FileType;
import com.mysuite.mytrade.message.api.generator.QuoteMessageGenerator;
import com.mysuite.mytrade.message.api.sinker.QuoteMessageFileSinker;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jianl on 26/05/2017.
 */
@Component
public class QuoteMessageFilterImpl extends AbstractLoggable implements QuoteMessageFilter {

    @Autowired
    private String reduceToFileRepository;
    @Autowired
    private QuoteMessageFileSinker quoteMessageFileSinker;
    @Autowired
    private QuoteMessageGenerator quoteMessageGenerator;

    public QuoteMessageFilterImpl() {
        super(LogFactory.getLog(QuoteMessageFilterImpl.class));
    }

    @Override
    public String quoteFileReduce(String securityCode, String originalFilename, Integer reducedByMaxNumberOfDaysGap, boolean skipHeader) throws MessageProcessException {
        try {
            Long maxNumberOfDaysAllowed = Long.valueOf(reducedByMaxNumberOfDaysGap) * 24 * 60 * 60;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(originalFilename), "UTF-8"));

            List<QuoteMessage> quoteMessageList = this.quoteMessageGenerator.generate(originalFilename, skipHeader);
            int j = 1;
            Long currentQuoteDate = 0L;
            Long nextQuoteDate = 0L;
            boolean reduce = false;
            List<QuoteMessage> reducedQuoteMessageList = new ArrayList<>();
            Collections.reverse(quoteMessageList);
            for (int i = 0; i < quoteMessageList.size(); i++) {
                QuoteMessage current = quoteMessageList.get(i);
                currentQuoteDate = DateTimeFormatHelper.getDateFromDateString(current.getQuotePart().getDate()).getTime() / 1000;
                if (j < quoteMessageList.size()) {
                    QuoteMessage next = quoteMessageList.get(j);
                    nextQuoteDate = DateTimeFormatHelper.getDateFromDateString(next.getQuotePart().getDate()).getTime() / 1000;
                    Long gap = currentQuoteDate - nextQuoteDate;
                    if (gap.compareTo(maxNumberOfDaysAllowed) < 0) {
                        current.getQuotePart().setPriceChange(current.getQuotePart().getAdjustClosedAt().subtract(next.getQuotePart().getAdjustClosedAt()));
                        current.getQuotePart().setLastDayClosedAt(next.getQuotePart().getAdjustClosedAt());
                    } else {
                        this.getLogger().warn("Max number of days gap identified. Reduce");
                        current.getQuotePart().setPriceChange(current.getQuotePart().getAdjustClosedAt().subtract(current.getQuotePart().getOpenedAt()));
                        current.getQuotePart().setLastDayClosedAt(current.getQuotePart().getClosedAt());
                        reduce = true;
                    }
                } else {
                    current.getQuotePart().setPriceChange(current.getQuotePart().getAdjustClosedAt().subtract(current.getQuotePart().getOpenedAt()));
                    current.getQuotePart().setLastDayClosedAt(current.getQuotePart().getClosedAt());
                }
                reducedQuoteMessageList.add(current);

                if (reduce) {
                    break;
                } else {
                    j++;
                }
            }

            if (reducedQuoteMessageList.size() > 0) {
                Collections.reverse(reducedQuoteMessageList);
                String targetFilename = this.reduceToFileRepository + "/" + securityCode + FileType.CSV.getSuffix();
                reducedQuoteMessageList.forEach(quoteMessage -> this.quoteMessageFileSinker.sinkToFile(quoteMessage, FileType.CSV, targetFilename));
                return targetFilename;
            } else {
                throw new MessageProcessException("Failed to filter quote message file: " + originalFilename + ". Filtered quote message list size is 0.");
            }
        } catch (Throwable e) {
            throw new MessageProcessException("Failed to filter quote message file: " + originalFilename + ".", e);
        }
    }

    @Override
    public List<QuoteMessage> quoteMessageReduce(List<QuoteMessage> quoteMessageList, Date beforeDate) throws MessageProcessException {
        Long beforeDateValue = beforeDate.getTime()/1000;
        return quoteMessageList.stream().filter(quoteMessage -> DateTimeFormatHelper.getDateFromDateString(quoteMessage.getQuotePart().getDate()).getTime()/1000 >= beforeDateValue).collect(Collectors.toList());
    }
}
