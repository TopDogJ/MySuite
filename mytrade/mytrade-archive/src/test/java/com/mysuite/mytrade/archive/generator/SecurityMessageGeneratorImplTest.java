package com.mysuite.mytrade.archive.generator;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.mycommons.test.api.SpringBaseTestCase;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created by jianl on 21/05/2017.
 */
public class SecurityMessageGeneratorImplTest extends SpringBaseTestCase {

    @Autowired
    private SecurityMessageGenerator securityMessageGenerator;
    @Autowired
    private EntityRepository<Security> securityEntityRepository;

    @Test
    @Transactional
    public void generateStandard() throws Exception, EntityNotFoundException {
//        Security security = this.securityEntityRepository.findByDuplicate(Arrays.asList("600000"));
//        SecurityMessage securityMessage = this.securityMessageGenerator.generateStandard(security);
//        System.out.println(securityMessage);
    }
}