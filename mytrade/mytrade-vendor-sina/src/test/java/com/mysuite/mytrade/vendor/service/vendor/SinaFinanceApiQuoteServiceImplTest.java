package com.mysuite.mytrade.vendor.service.vendor;

import com.mysuite.commons.exception.VendorDataNotFoundException;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jianl on 25/05/2017.
 */
public class SinaFinanceApiQuoteServiceImplTest extends SpringBaseTestCase {

    @Autowired
    SinaFinanceApiQuoteService sinaFinanceApiQuoteService;

    @Test
    public void fetchQuote() throws Exception, ServiceException {
        QuoteMessage quoteMessage = null;
        try {
            quoteMessage = this.sinaFinanceApiQuoteService.fetchQuote("sh", "600000");
            System.out.println(quoteMessage);
        } catch (VendorDataNotFoundException e) {
            assertEquals("No current data found.", e.getMessage());
        }
    }
}