package com.mysuite.entity.repositories.log;

import com.mysuite.entity.beans.log.Log;
import com.mysuite.entity.beans.log.LogType;
import com.mysuite.entity.support.EntityBean;
import com.mysuite.entity.support.EntityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jianl on 29/03/2017.
 */
@Repository
public class LogRepositoryImpl extends EntityRepository implements LogRepository  {

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Log log) {
        this.getSessionFactory().getCurrentSession().save(log);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Log log) {
        this.getSessionFactory().getCurrentSession().delete(log);
    }

    public List<Log> findAll() {
        return null;
    }

    public List<Log> findByType(LogType logType) {
        List<Log> logList = this.getSessionFactory().getCurrentSession().createQuery("from Log log where log.logType = ?").setParameter(0,logType).list();
        return logList;
    }
}
