package com.mysuite.mytrade.appl.controller.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.commons.utility.format.DateTimeFormatHelper;
import com.mysuite.mytrade.message.api.bean.security.indicator.macd.MACDIndicator;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RSIIndicator;
import com.mysuite.mytrade.message.api.bean.type.FileType;
import com.mysuite.mytrade.message.api.generator.indicator.MACDIndicatorGenerator;
import com.mysuite.mytrade.message.api.generator.indicator.RSIIndicatorGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jianl on 11/06/2017.
 */
@Controller
@RequestMapping("/indicator/macd")
public class MacdIndicatorController extends AbstractLoggable{

    @Autowired
    private MACDIndicatorGenerator macdIndicatorGenerator;
    @Autowired
    private String reduceToFileRepository;

    public MacdIndicatorController() {
        super(LogFactory.getLog(MacdIndicatorController.class));
    }

    @RequestMapping(value = "/daily", method = RequestMethod.POST)
    public @ResponseBody
    List<MACDIndicator> listDaily(@RequestParam String securityCode, @RequestParam String fromDate, HttpServletResponse httpServletResponse){
        String filename = this.reduceToFileRepository + "/" + securityCode + FileType.CSV.getSuffix();
        if(new File(filename).exists()){
            try {
                List<MACDIndicator> macdIndicators = this.macdIndicatorGenerator.generate(filename, 6, 12 ,24);
                Long fromDateValue = DateTimeFormatHelper.getDateFromDateString(fromDate).getTime();
                return macdIndicators.stream().filter(macdIndicator -> DateTimeFormatHelper.getDateFromDateString(macdIndicator.getDate()).getTime() >= fromDateValue).collect(Collectors.toList());
            } catch (MessageProcessException e) {
                this.getLogger().error("Failed to list macd indicator for security: " + securityCode, e);
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }else{
            this.getLogger().warn("No file found for security: " + securityCode);
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return null;
    }
}
