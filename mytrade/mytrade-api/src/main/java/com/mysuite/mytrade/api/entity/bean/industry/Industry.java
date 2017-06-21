package com.mysuite.mytrade.api.entity.bean.industry;

import com.mysuite.mytrade.api.entity.bean.EntityBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * Created by jianl on 19/05/2017.
 */
@Entity
public class Industry extends EntityBean {

    @Column(length = 20, unique = true, nullable = false)
    private String vendorCode;
    @Column(length = 20, unique = true, nullable = false)
    private String industryName;

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @Override
    protected void appendToString(StringBuffer stringBuffer) {
        stringBuffer.append(", vendorCode=" + vendorCode);
        stringBuffer.append(", industryName=" + industryName);
    }
}
