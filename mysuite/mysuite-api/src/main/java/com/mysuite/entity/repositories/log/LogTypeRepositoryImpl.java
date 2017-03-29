package com.mysuite.entity.repositories.log;

import com.mysuite.entity.beans.log.LogType;
import com.mysuite.entity.support.EntityRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jianl on 29/03/2017.
 */
public class LogTypeRepositoryImpl extends EntityRepository implements LogTypeRepository {

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(LogType logType) {
        this.getSessionFactory().getCurrentSession().save(logType);
    }
}
