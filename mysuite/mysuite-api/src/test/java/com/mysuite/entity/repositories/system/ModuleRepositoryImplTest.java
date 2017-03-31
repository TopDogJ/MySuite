package com.mysuite.entity.repositories.system;

import com.mysuite.entity.beans.system.Module;
import com.mysuite.entity.beans.system.Status;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jianl on 30/03/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ModuleRepositoryImplTest extends TestCase {

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    public void save() throws Exception {
        Status status = new Status();
        status.setName("ACTIVE");
        status.setCode(1);
        Module module = new Module();
        module.setName("TEST");
        module.setStatus(status);
        module.setUri("URI");
        moduleRepository.save(module);
    }

}