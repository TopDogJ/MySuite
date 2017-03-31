package com.mysuite.entity.beans.log;

import com.mysuite.entity.support.EntityBean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jianl on 29/03/2017.
 */
@MappedSuperclass
public abstract class Log extends EntityBean {

    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date time;
    @Enumerated(value = EnumType.STRING)
    private LogLevel logLevel;
    @Column(nullable = false)
    private String message;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected void appendToString(StringBuffer stringBuffer) {
        stringBuffer.append(", date=" + date);
        stringBuffer.append(", time=" + time);
        stringBuffer.append(", logLevel=" + logLevel.toString());
        stringBuffer.append(", message=" + message);
    }
}
