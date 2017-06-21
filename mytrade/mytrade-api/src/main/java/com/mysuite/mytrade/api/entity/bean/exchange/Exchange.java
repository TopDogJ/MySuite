package com.mysuite.mytrade.api.entity.bean.exchange;

import com.mysuite.mytrade.api.entity.bean.EntityBean;
import com.mysuite.mytrade.api.entity.bean.country.Country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by jianl on 9/04/2017.
 */
@Entity
public class Exchange extends EntityBean {

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String code;
    @ManyToOne
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    protected void appendToString(StringBuffer stringBuffer) {
        stringBuffer.append(", name=" + name);
        stringBuffer.append(", code=" + code);
        stringBuffer.append(", country=" + country.getId());
    }
}
