package com.mysuite.entity.beans.system;

import com.mysuite.entity.support.EntityBean;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by jianl on 29/03/2017.
 */
@Entity
public class Module extends EntityBean {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String uri;
    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    protected void appendToString(StringBuffer stringBuffer) {
        stringBuffer.append(", name=" + name);
        stringBuffer.append(", uri=" + uri);
        stringBuffer.append(", status="+ status.toString());
    }
}
