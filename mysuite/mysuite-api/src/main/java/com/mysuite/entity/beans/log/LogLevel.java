package com.mysuite.entity.beans.log;

import com.mysuite.entity.support.EntityBean;

/**
 * Created by jianl on 29/03/2017.
 */
public enum LogLevel {
    INFO(1),WARN(2),ERROR(3),FATAL(4),DEBUG(5),TRACE(6);

    private Integer code;

    private LogLevel(Integer code){
        this.code = code;
    }

}
