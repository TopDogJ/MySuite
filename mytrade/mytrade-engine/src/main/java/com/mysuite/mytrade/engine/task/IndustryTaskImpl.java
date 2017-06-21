package com.mysuite.mytrade.engine.task;

import com.mysuite.commons.log.AbstractLoggable;
import com.mysuite.mytrade.message.api.bean.vendor.sina.SinaVendorActionRequestMessage;
import com.mysuite.mytrade.message.api.producer.vendor.SinaVendorActionMessageProducer;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 19/05/2017.
 */
@Component
public class IndustryTaskImpl extends AbstractLoggable implements IndustryTask {

    @Autowired
    private SinaVendorActionMessageProducer sinaVendorActionMessageProducer;

    public IndustryTaskImpl() {
        super(LogFactory.getLog(IndustryTaskImpl.class));
    }

    @Override
    @Scheduled(cron = "0 0 18 ? * MON-FRI" )
    public void fetchAllIndustryTask() {
        SinaVendorActionRequestMessage sinaVendorActionMessage = new SinaVendorActionRequestMessage();
        sinaVendorActionMessage.setAction("fetch.industry.all");
        this.getLogger().info("Raise event to fetch industries: " + sinaVendorActionMessage);
        try {
            this.sinaVendorActionMessageProducer.produce(sinaVendorActionMessage);
        } catch (Throwable e) {
            this.getLogger().fatal("Failed to produce vendor action message." + e);
        }
    }
}
