package com.mysuite.entity.beans.log;

import com.mysuite.entity.support.EntityBean;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by jianl on 29/03/2017.
 */
@Entity
public class Log extends EntityBean {

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    private LogType logType;
    @Column(nullable = false)
    private String message;

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected void appendToString(StringBuffer stringBuffer) {
        stringBuffer.append(", logType=" + logType.toString());
        stringBuffer.append(", message=" + message);
    }
}
