package com.mysuite.entity.repositories.log;

import com.mysuite.entity.beans.log.LogLevel;
import com.mysuite.entity.beans.log.SystemLog;
import com.mysuite.entity.support.EntityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by jianl on 30/03/2017.
 */
@Repository
public class SystemLogRepositoryImpl extends EntityRepository implements SystemLogRepository {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(SystemLog systemLog) {
        this.getSessionFactory().getCurrentSession().save(systemLog);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(SystemLog systemLog) {
        this.getSessionFactory().getCurrentSession().update(systemLog);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(SystemLog systemLog) {
        this.getSessionFactory().getCurrentSession().delete(systemLog);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SystemLog> findAll() {
        return this.getSessionFactory().getCurrentSession().createQuery("from SystemLog").list();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SystemLog> findByLevel(LogLevel logLevel) {
        return this.getSessionFactory().getCurrentSession().createQuery("from SystemLog systemLog where systemLog.logLevel = ?").setParameter(0,logLevel).list();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SystemLog> findByComponent(String component) {
        return this.getSessionFactory().getCurrentSession().createQuery("from SystemLog systemLog where systemLog.component= ?").setParameter(0,component).list();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SystemLog> findByDate(Date date) {
        return null;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SystemLog> findByTime(Date date) {
        return null;
    }
}
