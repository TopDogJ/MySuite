package com.mysuite.mytrade.message.api.bean.security.part;

import java.io.Serializable;

/**
 * Created by jianl on 16/05/2017.
 */
public class ProfilePart implements Serializable {
    private String securityName;
    private String securityCode;
    private String exchangeCode;
    private String exchangeTypeCode;

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
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

    @Override
    public String toString() {
        return "Profile{" +
                "securityName='" + securityName + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", exchangeTypeCode='" + exchangeTypeCode + '\'' +
                '}';
    }
}
