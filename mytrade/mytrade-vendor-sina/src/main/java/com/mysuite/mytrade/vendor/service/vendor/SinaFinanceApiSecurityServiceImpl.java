package com.mysuite.mytrade.vendor.service.vendor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysuite.commons.exception.VendorDataNotFoundException;
import com.mysuite.commons.exception.service.HttpServiceException;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.commons.exception.service.ServiceParsingException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import com.mysuite.mytrade.message.api.bean.security.part.PortfolioPart;
import com.mysuite.mytrade.message.api.bean.security.part.ProfilePart;
import com.mysuite.mytrade.message.api.bean.vendor.sina.IndustrySecurity;
import com.mysuite.mytrade.vendor.service.http.HttpService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jianl on 28/04/2017.
 */
@Service
public class SinaFinanceApiSecurityServiceImpl extends AbstractLoggable implements SinaFinanceApiSecurityService {

    private static final String FETCH_SECURITY_URL = "http://hq.sinajs.cn/";
    private static final String FETCH_PORTFOLIO_URL = "http://finance.sina.com.cn/realstock/company/";
    private static final String FETCH_PORTFOLIO_URL_SUFFIX = "/jsvar.js";
    private static final String FETCH_SECURITY_BY_INDUSTRY_URL = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?";

    @Autowired
    private HttpService httpService;
    @Autowired
    private SinaFinanceApiIndustryService sinaFinanceApiIndustryService;

    public SinaFinanceApiSecurityServiceImpl() {
        super(LogFactory.getLog(SinaFinanceApiSecurityServiceImpl.class));
    }

    @Override
    public SecurityMessage fetchSecurityDetailsByCode(String exchangeCode, String securityCode) throws ServiceException, VendorDataNotFoundException {
        return this.fetchSecurity(exchangeCode, securityCode);
    }

    private SecurityMessage fetchSecurity(final String exchangeCode, final String securityCode) throws ServiceException, VendorDataNotFoundException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("list", exchangeCode + securityCode);
        String response = this.httpService.doGet(FETCH_SECURITY_URL, params);

        SecurityMessage result = this.createSecurityMessage(response, exchangeCode);

        return result;
    }

    private SecurityMessage createSecurityMessage(final String response, final String exchangeCode) throws ServiceException, VendorDataNotFoundException {
        String result = response.replace("var hq_str_sh", "").replace("=\"", ",").replace("\";", "").trim();
        String[] datas = result.split(",");
        SecurityMessage securityMessage = new SecurityMessage();
        securityMessage.setProfile(this.assembleProfile(datas[1], datas[0], exchangeCode));
        this.createPortfolio(securityMessage);
        return securityMessage;
    }


    @Override
    public List<SecurityMessage> fetchSecurityDetailsByIndustry(String exchangeCode, String exchangeTypeCode, String vendorIndustryCode) throws ServiceException, VendorDataNotFoundException {
        List<SecurityMessage> result = new ArrayList<>();

        if (vendorIndustryCode == null) {
            List<IndustryMessage> industries = this.sinaFinanceApiIndustryService.createAllIndustryMessage();
            for (IndustryMessage industry : industries) {
                List<SecurityMessage> securityMessageList = this.createSecurityMessagesByIndustry(exchangeCode, exchangeTypeCode, industry);
                securityMessageList.forEach(securityMessage -> result.add(securityMessage));
            }
        } else {
            IndustryMessage industry = this.sinaFinanceApiIndustryService.createIndustryMessageByCode(vendorIndustryCode);
            return this.createSecurityMessagesByIndustry(exchangeCode, exchangeTypeCode, industry);
        }
        return result;
    }

    private List<SecurityMessage> createSecurityMessagesByIndustry(final String exchangeCode, final String exchangeTypeCode, final IndustryMessage industryMessage) throws ServiceException {
        Map<String, String> param = new HashMap<>();
        param.put("node", industryMessage.getIndustryProfilePart().getVendorCode());
        param.put("page", "1");
        param.put("num", industryMessage.getIndustryIndexPart().getNumberOfSecurities());
        String response = this.httpService.doGet(FETCH_SECURITY_BY_INDUSTRY_URL, param);
        if (response == null) {
            throw new ServiceException("Failed to fetch security details by industry: " + industryMessage.getIndustryProfilePart().getVendorCode() + " expected number of record: " + industryMessage.getIndustryIndexPart().getNumberOfSecurities());
        }
        try {
            return this.assembleSecurityMessages(exchangeCode, exchangeTypeCode, response, industryMessage);
        } catch (Throwable e) {
            throw new ServiceException("Failed to fetch security by industry category.", e);
        }
    }

    private List<SecurityMessage> assembleSecurityMessages(final String exchangeCode, final String exchangeTypeCode, final String response, final IndustryMessage industryMessage) throws ServiceParsingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndustrySecurity.class);
        List<SecurityMessage> result = new ArrayList<>();
        try {
            List<IndustrySecurity> industrySecurities = objectMapper.readValue(response, javaType);

            for (IndustrySecurity industrySecurity : industrySecurities) {
                SecurityMessage securityMessage = new SecurityMessage();
                if (exchangeCode != null) {
                    if (industrySecurity.getSymbol().substring(0, 2).equals(exchangeCode)) {
                        securityMessage.setProfile(this.assembleProfile(industrySecurity.getName(), industrySecurity.getCode(), industrySecurity.getSymbol().substring(0, 2)));
                        securityMessage.setIndustry(industryMessage.getIndustryProfilePart());
                        this.createPortfolio(securityMessage);
                        if (exchangeTypeCode != null) {
                            if (securityMessage.getProfile().getExchangeTypeCode().equals(exchangeTypeCode)) {
                                result.add(securityMessage);
                            }
                        } else {
                            result.add(securityMessage);
                        }
                    } else {
                        this.getLogger().debug("Security with exchange code: " + industrySecurity.getSymbol().substring(0, 2) + "filtered out.");
                    }
                } else {
                    securityMessage.setProfile(this.assembleProfile(industrySecurity.getName(), industrySecurity.getCode(), industrySecurity.getSymbol().substring(0, 2)));
                    securityMessage.setIndustry(industryMessage.getIndustryProfilePart());
                    this.createPortfolio(securityMessage);
                    if (exchangeTypeCode != null) {
                        if (securityMessage.getProfile().getExchangeTypeCode().equals(exchangeTypeCode)) {
                            result.add(securityMessage);
                        } else {
                            result.add(securityMessage);
                        }
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            throw new ServiceParsingException("Failed to parse to industry security", e);
        }
    }

    private void createPortfolio(final SecurityMessage securityMessage) throws HttpServiceException {
        String response = this.httpService.doGet(FETCH_PORTFOLIO_URL + securityMessage.getProfile().getExchangeCode() + securityMessage.getProfile().getSecurityCode() + FETCH_PORTFOLIO_URL_SUFFIX);
        Map<String, String> resultMap = this.createPortfolioResultMap(response);
        if (!(resultMap.get("stock_state").equals("1"))) {
            this.getLogger().warn("Security: " + securityMessage.getProfile().getSecurityCode() + "is currently not in market.");
        } else {
            securityMessage.setPortfolio(this.assemblePortfolio(resultMap));
            securityMessage.getProfile().setExchangeTypeCode(resultMap.get("stockType"));
        }
    }

    private PortfolioPart assemblePortfolio(Map<String, String> resultMap) {
        PortfolioPart portfolio = new PortfolioPart();
        portfolio.setTotalCapitalVolume(new BigDecimal(resultMap.get("totalcapital")).multiply(new BigDecimal(10000)).longValue());
        portfolio.setTotalExchangedVolume(new BigDecimal(resultMap.get("currcapital")).multiply(new BigDecimal(10000)).longValue());
        portfolio.setNetValuePerShare(new BigDecimal(resultMap.get("mgjzc")));
        portfolio.setLastFourQuarterTotalEPS(new BigDecimal(resultMap.get("fourQ_mgsy")));
        portfolio.setLastYearTotalEPS(new BigDecimal(resultMap.get("lastyear_mgsy")));
        portfolio.setLastFourQuarterNetProfit(new BigDecimal(resultMap.get("profit_four")));
        portfolio.setLastYearNetProfit(new BigDecimal(resultMap.get("profit")));
        return portfolio;
    }

    private Map<String, String> createPortfolioResultMap(String responseString) {
        List<String> datas = Arrays.asList(responseString.split("\n"));
        Map<String, String> resultMap = new HashMap<>();
        for (String s : datas) {
            String str = s.split(";")[0].replaceAll("var", "").replaceAll(" ", "").replace("'", "").trim();
            String[] result = str.split("=");
            if (result.length == 1) {
                resultMap.put(result[0], null);
            } else {
                resultMap.put(result[0], result[1]);
            }
        }
        return resultMap;
    }

    private ProfilePart assembleProfile(String securityName, String securityCode, String exchangeCode) {
        ProfilePart profile = new ProfilePart();
        profile.setSecurityName(securityName);
        profile.setSecurityCode(securityCode);
        profile.setExchangeCode(exchangeCode);
        return profile;
    }

}

//    private BigDecimal createCurrentDayAverageVolumePerMinute(final Long totalVolume, final String date, final String time) {
//
//        Date currentDate = new Date(System.currentTimeMillis());
//
//        BigDecimal result = BigDecimal.ZERO;
//        if (DateTimeFormatHelper.getDateString(currentDate).equals(date)) {
//            Long open = DateTimeFormatHelper.getTimeFromTimeString("09:30:00").getTime();
//            Long current = DateTimeFormatHelper.getTimeFromTimeString(time).getTime();
//            Long midDayStart = DateTimeFormatHelper.getTimeFromTimeString("11:30:00").getTime();
//            Long midDayEnd =DateTimeFormatHelper.getTimeFromTimeString("13:00:00").getTime();
//
//            if(current.compareTo(midDayStart) == 1 && current.compareTo(midDayEnd) == -1){
//                current = midDayStart;
//            }else if (current.compareTo(midDayStart) == 1 && current.compareTo(midDayEnd) >= 0){
//                current = current - (60*1000*90);
//            }else{
//                current = current;
//            }
//
//            Long totalIntradeSeconds = current - open;
//            BigDecimal totalIntradeMinute = new BigDecimal(totalIntradeSeconds).divide(new BigDecimal(1000).multiply(new BigDecimal(60)), 0, BigDecimal.ROUND_HALF_UP);
//            result = new BigDecimal(totalVolume).divide(totalIntradeMinute, 6, BigDecimal.ROUND_HALF_UP);
//        } else {
//            BigDecimal totalIntradeMinute = new BigDecimal(240);
//            result = new BigDecimal(totalVolume).divide(totalIntradeMinute, 6, BigDecimal.ROUND_HALF_UP);
//        }
//
//        return result;
//    }
