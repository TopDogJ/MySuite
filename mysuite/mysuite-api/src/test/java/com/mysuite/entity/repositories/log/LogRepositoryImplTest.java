package com.mysuite.entity.repositories.log;

import com.mysuite.entity.beans.log.Log;
import com.mysuite.entity.beans.log.LogType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jianl on 29/03/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class LogRepositoryImplTest {

    @Autowired
    private LogRepository logRepository;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Rollback(false)
    public void doTest() throws Exception {

        LogType logType = new LogType();

        logType.setName("SYSTEM");
        Log log = new Log();
        log.setDate(new Date());
        log.setTime(new Date());
        log.setMessage("System log created");
        logRepository.save(log);

        log.setLogType(logType);
        Log log2 = new Log();
        log2.setDate(new Date());
        log2.setTime(new Date());
        log2.setMessage("System log2 created");
        log2.setLogType(logType);
        logRepository.save(log2);

        List<Log> logList = logRepository.findByType(logType);
        for (Log l:logList) {
            System.out.println(l.toString());
        }

        logRepository.delete(log);
    }
}