package com.mysuite.mytrade.engine.exporter;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.engine.generator.SecurityMessageGenerator;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jianl on 20/05/2017.
 */

public class SecurityMessageFileExporterImplTest extends SpringBaseTestCase{

    @Autowired
    private SecurityMessageFileExporter securityMessageFileExporter;
    @Autowired
    private SecurityMessageGenerator securityMessageGenerator;
    @Autowired
    private EntityRepository<Security> securityEntityRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void exportToFile() throws Exception, EntityNotFoundException {
//        List<Security> securityList = this.securityEntityRepository.findAllForReference();
//        for (Security security : securityList) {
//            if(security.getProfile().getExchangeType().getExchange().getCode().equals("sh") && security.getSecurityStatus().getCode().equals("N") && security.getProfile().getExchangeType().getCode().equals("A")){
//                SecurityMessage securityMessage = this.securityMessageGenerator.generateStandard(security);
//                this.securityMessageFileExporter.exportToFile(securityMessage,"ALL_SH_A_SECURITY.json");
//            }
//        }
    }
}