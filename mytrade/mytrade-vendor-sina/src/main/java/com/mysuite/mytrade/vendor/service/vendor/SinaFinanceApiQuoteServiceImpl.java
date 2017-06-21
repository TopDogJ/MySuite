package com.mysuite.mytrade.vendor.service.vendor;

import com.mysuite.commons.exception.VendorDataNotFoundException;
import com.mysuite.commons.exception.service.HttpServiceException;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.commons.utility.format.DateTimeFormatHelper;
import com.mysuite.mytrade.message.api.bean.security.part.ProfilePart;
import com.mysuite.mytrade.message.api.bean.security.part.TransactionPart;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuotePart;
import com.mysuite.mytrade.message.api.bean.security.quote.RealtimeQuoteMessage;
import com.mysuite.mytrade.vendor.service.http.HttpService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jianl on 25/05/2017.
 */
@Component
public class SinaFinanceApiQuoteServiceImpl extends AbstractLoggable implements SinaFinanceApiQuoteService {

    private static final String FETCH_QUOTE_URL = "http://hq.sinajs.cn/";

    @Autowired
    private HttpService httpService;

    public SinaFinanceApiQuoteServiceImpl() {
        super(LogFactory.getLog(SinaFinanceApiQuoteServiceImpl.class));
    }

    @Override
    public QuoteMessage fetchQuote(final String exchangeCode, final String securityCode) throws ServiceException, VendorDataNotFoundException {
        return this.fetchQuoteForSecurity(exchangeCode, securityCode);
    }

    private QuoteMessage fetchQuoteForSecurity(String exchangeCode, String securityCode) throws HttpServiceException, VendorDataNotFoundException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("list", exchangeCode + securityCode);
        String response = this.httpService.doGet(FETCH_QUOTE_URL, params);
        return this.assembleQuoteMessage(response, exchangeCode, securityCode);
    }

    private QuoteMessage assembleQuoteMessage(String response, String exchangeCode, String securityCode) throws VendorDataNotFoundException {
        String result = response.replace("var hq_str_sh", "").replace("=\"", ",").replace("\";", "").trim();
        String[] datas = result.split(",");
        QuoteMessage quoteMessage = new QuoteMessage();
        quoteMessage.setQuotePart(this.assembleQuotePart(datas));
        ProfilePart profilePart = new ProfilePart();
        profilePart.setExchangeCode(exchangeCode);
        profilePart.setSecurityCode(securityCode);
        profilePart.setSecurityName(datas[1]);
        quoteMessage.setSecurityProfile(profilePart);
        return quoteMessage;
    }

    private QuotePart assembleQuotePart(String[] datas) throws VendorDataNotFoundException {
        QuotePart quote = new QuotePart();
        if(!datas[31].equals(DateTimeFormatHelper.getDateString(new Date(System.currentTimeMillis())))){
            throw new VendorDataNotFoundException("No current data found.");
        }else if(new BigDecimal(datas[2]).compareTo(BigDecimal.ZERO) == 0){
            throw new VendorDataNotFoundException("No current data found.");
        }
        quote.setOpenedAt(new BigDecimal(datas[2]));
        quote.setLastDayClosedAt(new BigDecimal(datas[3]));
        quote.setCurrentAt(new BigDecimal(datas[4]));
        quote.setHighestAt(new BigDecimal(datas[5]));
        quote.setLowestAt(new BigDecimal(datas[6]));

        quote.setBuyAt(new BigDecimal(datas[7]));
        quote.setSellAt(new BigDecimal(datas[8]));

        quote.setTotalVolume(Long.valueOf(datas[9]));
        quote.setTotalValue(new BigDecimal(datas[10]).multiply(new BigDecimal(10000)));

        quote.setDate(datas[31]);
        quote.setTime(datas[32]);

        quote.setLodgedBuys(this.assembleLodgeTransactions(datas, "B"));
        quote.setLodgedSell(this.assembleLodgeTransactions(datas, "S"));

        quote.setPriceChange(quote.getCurrentAt().subtract(quote.getLastDayClosedAt()));
        quote.setHighestAllowedAt(quote.getLastDayClosedAt().add(quote.getLastDayClosedAt().multiply(new BigDecimal(0.1).setScale(2, BigDecimal.ROUND_HALF_UP))));
        quote.setLowestAllowedAt(quote.getLastDayClosedAt().subtract(quote.getLastDayClosedAt().multiply(new BigDecimal(0.1).setScale(2, BigDecimal.ROUND_HALF_UP))));

        return quote;
    }

    private List<TransactionPart> assembleLodgeTransactions(String[] datas, String type) {
        List<TransactionPart> transactions = new ArrayList<>();
        switch (type) {
            case ("B"):
                transactions.add(this.assembleTransaction(new Long(datas[11]), new BigDecimal(datas[12]), type, 1));
                transactions.add(this.assembleTransaction(new Long(datas[13]), new BigDecimal(datas[14]), type, 2));
                transactions.add(this.assembleTransaction(new Long(datas[15]), new BigDecimal(datas[16]), type, 3));
                transactions.add(this.assembleTransaction(new Long(datas[17]), new BigDecimal(datas[18]), type, 4));
                transactions.add(this.assembleTransaction(new Long(datas[19]), new BigDecimal(datas[20]), type, 5));
                break;
            case ("S"):
                transactions.add(this.assembleTransaction(new Long(datas[21]), new BigDecimal(datas[22]), type, 1));
                transactions.add(this.assembleTransaction(new Long(datas[23]), new BigDecimal(datas[24]), type, 2));
                transactions.add(this.assembleTransaction(new Long(datas[25]), new BigDecimal(datas[26]), type, 3));
                transactions.add(this.assembleTransaction(new Long(datas[27]), new BigDecimal(datas[28]), type, 4));
                transactions.add(this.assembleTransaction(new Long(datas[29]), new BigDecimal(datas[30]), type, 5));
                break;
            default:
                break;
        }
        return transactions;
    }

    private TransactionPart assembleTransaction(Long volume, BigDecimal price, String type, int position) {
        TransactionPart transaction = new TransactionPart();
        transaction.setVolume(volume);
        transaction.setPrice(price);
        transaction.setType(type);
        transaction.setPosition(position);
        return transaction;
    }
}
