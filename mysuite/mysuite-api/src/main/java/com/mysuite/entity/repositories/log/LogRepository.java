package com.mysuite.entity.repositories.log;

import com.mysuite.entity.beans.log.Log;
import com.mysuite.entity.beans.log.LogType;

import java.util.List;

/**
 * Created by jianl on 29/03/2017.
 */
public interface LogRepository {

    public void save(final Log log);
    public void delete(final Log log);
    public List<Log> findAll();
    public List<Log> findByType(final LogType logType);

}
