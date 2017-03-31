package com.mysuite.entity.repositories.log;

import com.mysuite.entity.beans.log.LogLevel;
import com.mysuite.entity.beans.log.SystemLog;

import java.util.Date;
import java.util.List;

/**
 * Created by jianl on 30/03/2017.
 */
public interface SystemLogRepository {
    public void save(final SystemLog systemLog);
    public void update(final SystemLog systemLog);
    public void delete(final SystemLog systemLog);
    public List<SystemLog> findAll();
    public List<SystemLog> findByLevel(final LogLevel logLevel);
    public List<SystemLog> findByComponent(final String component);
    public List<SystemLog> findByDate( final Date date);
    public List<SystemLog> findByTime( final Date date);
}
