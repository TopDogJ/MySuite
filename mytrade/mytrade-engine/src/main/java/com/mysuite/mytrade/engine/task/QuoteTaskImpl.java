package com.mysuite.mytrade.engine.task;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionRequestType;
import com.mysuite.mytrade.message.api.bean.vendor.sina.SinaVendorActionRequestMessage;
import com.mysuite.mytrade.message.api.producer.vendor.SinaVendorActionMessageProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jianl on 1/06/2017.
 */
@Component
public class QuoteTaskImpl extends AbstractLoggable implements QuoteTask {

    @Autowired
    private EntityRepository<Security> securityEntityRepository;
    @Autowired
    private SinaVendorActionMessageProducer sinaVendorActionMessageProducer;

    public QuoteTaskImpl() {
        super(LogFactory.getLog(QuoteTaskImpl.class));
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 * 09-15 ? *  MON-FRI")
    public void fetchQuoteRealtimeTask() {
        try {
            List<Security> securityList = this.securityEntityRepository.findAllForReference();
            for (Security security: securityList) {
                if(security.getProfile().getExchangeType().getExchange().getCode().equals("sh") && security.getProfile().getExchangeType().getCode().equals("A")){
                    SinaVendorActionRequestMessage sinaVendorActionRequestMessage = new SinaVendorActionRequestMessage();
                    sinaVendorActionRequestMessage.setSecurityCode(security.getCode());
                    sinaVendorActionRequestMessage.setExchangeCode(security.getProfile().getExchangeType().getExchange().getCode());
                    sinaVendorActionRequestMessage.setAction("fetch.quote");
                    sinaVendorActionRequestMessage.setVendorActionRequestType(VendorActionRequestType.RT);
                    this.sinaVendorActionMessageProducer.produce(sinaVendorActionRequestMessage);
                }
            }
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to perform fetch quote realtime task.",e);
        }
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 15 16 ? *  MON-FRI")
    public void fetchQuoteArchiveTask() {
        try {
            List<Security> securityList = this.securityEntityRepository.findAllForReference();
            for (Security security: securityList) {
                if(security.getProfile().getExchangeType().getExchange().getCode().equals("sh") && security.getProfile().getExchangeType().getCode().equals("A")){
                    SinaVendorActionRequestMessage sinaVendorActionRequestMessage = new SinaVendorActionRequestMessage();
                    sinaVendorActionRequestMessage.setSecurityCode(security.getCode());
                    sinaVendorActionRequestMessage.setExchangeCode(security.getProfile().getExchangeType().getExchange().getCode());
                    sinaVendorActionRequestMessage.setAction("fetch.quote");
                    sinaVendorActionRequestMessage.setVendorActionRequestType(VendorActionRequestType.ARCHIVE);
                    this.sinaVendorActionMessageProducer.produce(sinaVendorActionRequestMessage);
                }
            }
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to perform fetch quote realtime task.",e);
        }
    }
}
