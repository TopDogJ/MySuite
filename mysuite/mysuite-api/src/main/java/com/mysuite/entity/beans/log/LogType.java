package com.mysuite.entity.beans.log;

import com.mysuite.entity.support.EntityBean;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by jianl on 29/03/2017.
 */
@Entity
public class LogType extends EntityBean{

    @Column(nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void appendToString(StringBuffer stringBuffer) {
        stringBuffer.append(", name" + name);
    }
}
