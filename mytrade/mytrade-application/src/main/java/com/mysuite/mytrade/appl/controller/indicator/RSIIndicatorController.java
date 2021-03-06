package com.mysuite.mytrade.appl.controller.indicator;

import com.mysuite.commons.exception.process.MessageProcessException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.commons.utility.format.DateTimeFormatHelper;
import com.mysuite.mytrade.message.api.bean.security.indicator.rsi.RSIIndicator;
import com.mysuite.mytrade.message.api.bean.type.FileType;
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
@RequestMapping("/indicator/rsi")
public class RSIIndicatorController extends AbstractLoggable{

    @Autowired
    private RSIIndicatorGenerator rsiIndicatorGenerator;
    @Autowired
    private String reduceToFileRepository;

    public RSIIndicatorController() {
        super(LogFactory.getLog(RSIIndicatorController.class));
    }

    @RequestMapping(value = "/daily", method = RequestMethod.POST)
    public @ResponseBody
    List<RSIIndicator> listDaily(@RequestParam String securityCode, @RequestParam String fromDate, HttpServletResponse httpServletResponse){
        String filename = this.reduceToFileRepository + "/" + securityCode + FileType.CSV.getSuffix();
        if(new File(filename).exists()){
            try {
                List<RSIIndicator> rsiIndicators = this.rsiIndicatorGenerator.generate(filename, 6, 12 ,24);
                Long fromDateValue = DateTimeFormatHelper.getDateFromDateString(fromDate).getTime();
                return rsiIndicators.stream().filter(rsiIndicator -> DateTimeFormatHelper.getDateFromDateString(rsiIndicator.getDate()).getTime() >= fromDateValue).collect(Collectors.toList());
            } catch (MessageProcessException e) {
                this.getLogger().error("Failed to list rsi indicator for security: " + securityCode, e);
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }else{
            this.getLogger().warn("No file found for security: " + securityCode);
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return null;
    }
}
