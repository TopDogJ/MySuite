package com.mysuite.mytrade.api.entity.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jianl on 10/04/2017.
 */
@MappedSuperclass
public abstract class EntityBean implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getClass().getSimpleName());
        stringBuffer.append("{");
        stringBuffer.append("id=" + this.id);
        this.appendToString(stringBuffer);
        stringBuffer.append(", version=" + version);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    protected abstract void appendToString(StringBuffer stringBuffer);
}
