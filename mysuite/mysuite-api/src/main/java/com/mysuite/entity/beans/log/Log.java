package com.mysuite.entity.beans.log;

import com.mysuite.entity.support.EntityBean;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jianl on 29/03/2017.
 */
@Entity
public class Log extends EntityBean {

    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date time;
    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    private LogType logType;
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
        stringBuffer.append(", date=" + date);
        stringBuffer.append(", time=" + time);
        stringBuffer.append(", logType=" + logType.toString());
        stringBuffer.append(", message=" + message);
    }
}
