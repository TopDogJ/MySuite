package com.mysuite.entity.beans.system;

import com.mysuite.entity.support.EntityBean;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by jianl on 29/03/2017.
 */
@Entity
public class Status extends EntityBean {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private Integer code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    protected void appendToString(final StringBuffer stringBuffer) {
        stringBuffer.append(", name=" + name);
        stringBuffer.append(", code=" + code);
    }
}
