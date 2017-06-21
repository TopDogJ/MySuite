package com.mysuite.mytrade.appl.controller;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.commons.utility.format.DateTimeFormatHelper;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.appl.generator.SecurityMessageGenerator;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.quote.QuoteMessage;
import com.mysuite.mytrade.message.api.bean.type.FileType;
import com.mysuite.mytrade.message.api.filter.QuoteMessageFilter;
import com.mysuite.mytrade.message.api.generator.QuoteMessageGenerator;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by jianl on 29/05/2017.
 */
@Controller
@RequestMapping("/quote")
public class QuoteController extends AbstractLoggable {

    private static final String DEFAULT_REDUCE_FROM_DATE = "2016-01-01";
    private static final String DEFAULT_TIME_STAMP = "09:00:00";

    @Autowired
    private QuoteMessageFilter quoteMessageFilter;
    @Autowired
    private String reduceToFileRepository;
    @Autowired
    private QuoteMessageGenerator quoteMessageGenerator;
    @Autowired
    private String realtimeFileRepository;
    @Autowired
    private EntityRepository<Security> securityEntityRepository;
    @Autowired
    private SecurityMessageGenerator securityMessageGenerator;

    public QuoteController() {
        super(LogFactory.getLog(QuoteController.class));
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public
    @ResponseBody
    List<QuoteMessage> listQuote(@RequestParam String securityCode, @RequestParam String fromDate) throws MessageProcessException {
        if(fromDate == null || fromDate.equals("")){
            fromDate = DEFAULT_REDUCE_FROM_DATE;
        }
        this.getLogger().info("Request received to list quote for security: " + securityCode);
        String filename = this.reduceToFileRepository + "/" + securityCode + FileType.CSV.getSuffix();
        List<QuoteMessage> quoteMessageList = this.quoteMessageGenerator.generate(filename,false);
        List<QuoteMessage> resultList = this.quoteMessageFilter.quoteMessageReduce(quoteMessageList, DateTimeFormatHelper.getDateFromDateString(fromDate));
        return resultList;
    }

    @RequestMapping(value = "list/realtime", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @Transactional
    public
    @ResponseBody
    List<QuoteMessage> listRealtimeQuote(@RequestParam String securityCode, @RequestParam String timestamp, HttpServletResponse httpServletResponse) {
        if(timestamp == null || timestamp.equals("")){
            timestamp = DEFAULT_TIME_STAMP;
        }
        try{
            this.getLogger().info("Request received to list quote for security: " + securityCode);
            Security security = this.securityEntityRepository.findByDuplicateForReference(Arrays.asList(securityCode));
            SecurityMessage securityMessage  =securityMessageGenerator.generateStandard(security);
            String filename = this.realtimeFileRepository + "/" +DateTimeFormatHelper.getDateString(new Date(System.currentTimeMillis())) + "/" + securityCode + FileType.JSON.getSuffix();
            List<QuoteMessage> quoteMessageList = this.quoteMessageGenerator.generateLatest(filename, DateTimeFormatHelper.getTimeFromTimeString(timestamp).getTime(), securityMessage.getProfile(), securityMessage.getPortfolio());
            return quoteMessageList;
        }catch (Throwable e){
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }
}
