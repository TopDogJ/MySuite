package com.mysuite.mytrade.message.api.bean.vendor.sina;

import com.mysuite.mytrade.message.api.bean.MessagingBean;
import com.mysuite.mytrade.message.api.bean.vendor.VendorActionRequestType;

/**
 * Created by jianl on 17/05/2017.
 */
public class SinaVendorActionRequestMessage extends MessagingBean {

    private String action;
    private VendorActionRequestType vendorActionRequestType;
    private String exchangeCode;
    private String exchangeTypeCode;
    private String securityCode;
    private String industryCode;

    public SinaVendorActionRequestMessage() {
        super("sina.vendor.action");
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public VendorActionRequestType getVendorActionRequestType() {
        return vendorActionRequestType;
    }

    public void setVendorActionRequestType(VendorActionRequestType vendorActionRequestType) {
        this.vendorActionRequestType = vendorActionRequestType;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getExchangeTypeCode() {
        return exchangeTypeCode;
    }

    public void setExchangeTypeCode(String exchangeTypeCode) {
        this.exchangeTypeCode = exchangeTypeCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    @Override
    public String toString() {
        return "SinaVendorActionRequestMessage{" +
                "action='" + action + '\'' +
                ", vendorActionRequestType=" + vendorActionRequestType +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", exchangeTypeCode='" + exchangeTypeCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", industryCode='" + industryCode + '\'' +
                '}';
    }
}
