package com.mysuite.mytrade.engine.task;

import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.api.entity.bean.industry.Industry;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.engine.generator.SecurityMessageGenerator;
import com.mysuite.mytrade.message.api.bean.vendor.sina.SinaVendorActionRequestMessage;
import com.mysuite.mytrade.message.api.producer.vendor.SinaVendorActionMessageProducer;
import com.mysuite.mytrade.message.api.producer.vendor.YahooVendorActionMessageProducer;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jianl on 21/04/2017.
 */
@Component
public class SecurityTaskImpl extends AbstractLoggable implements SecurityTask {

    @Autowired
    private SinaVendorActionMessageProducer sinaVendorActionMessageProducer;
    @Autowired
    private YahooVendorActionMessageProducer yahooVendorActionMessageProducer;
    @Autowired
    @Qualifier("industryEntityRepository")
    private EntityRepository<Industry> industryEntityRepository;
    @Autowired
    @Qualifier("securityEntityRepository")
    private EntityRepository<Security> securityEntityRepository;
    @Autowired
    private SecurityMessageGenerator securityMessageGenerator;

    public SecurityTaskImpl() {
        super(LogFactory.getLog(SecurityTaskImpl.class));
    }

    @Override
    @Scheduled(cron = "0 10 18 ? * MON-FRI" )
    @Transactional
    public void fetchSecurityDetailsByIndustryTask(){
        try {
            List<Industry> industryList = this.industryEntityRepository.findAllForReference();
            for (Industry industry: industryList) {
                SinaVendorActionRequestMessage sinaVendorActionMessage = new SinaVendorActionRequestMessage();
                sinaVendorActionMessage.setAction("fetch.security.industry");
                sinaVendorActionMessage.setIndustryCode(industry.getVendorCode());
                sinaVendorActionMessage.setExchangeTypeCode("A");
                this.sinaVendorActionMessageProducer.produce(sinaVendorActionMessage);
                this.getLogger().info("Raise event to fetch security details by industry: " + sinaVendorActionMessage);
            }
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to perform fetch security details by industry.",e);
        }
    }
}
