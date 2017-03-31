package com.mysuite.entity.beans.log;

import com.mysuite.entity.beans.log.Log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by jianl on 30/03/2017.
 */
@Entity
public class SystemLog extends Log {

    @Column(nullable = false)
    private String component;
    @Column(nullable = false)
    private String operation;
    @Column(nullable = false)
    private Long operationTime;
    @Column()
    private String operationArgs;

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationArgs() {
        return operationArgs;
    }

    public void setOperationArgs(String operationArgs) {
        this.operationArgs = operationArgs;
    }

    @Override
    protected void appendToString(StringBuffer stringBuffer) {
        stringBuffer.append(", component=" + component);
        stringBuffer.append(", operation=" + operation);
        stringBuffer.append(", operationTime=" + operationTime);
        stringBuffer.append(", operationArgs=" + operationArgs);
        super.appendToString(stringBuffer);

    }
}
