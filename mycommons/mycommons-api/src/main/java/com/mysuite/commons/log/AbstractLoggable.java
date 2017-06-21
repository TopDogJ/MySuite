package com.mysuite.commons.log;

import org.apache.commons.logging.Log;

import java.math.BigDecimal;

/**
 * Created by jianl on 30/04/2017.
 */
public abstract class AbstractLoggable {

    private Log logger;

    public AbstractLoggable(Log logger) {
        this.logger = logger;
    }

    public Log getLogger() {
        return logger;
    }
}
