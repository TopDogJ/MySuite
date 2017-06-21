package com.mysuite.mytrade.message.api.bean.vendor.Yahoo;

import com.mysuite.mytrade.message.api.bean.MessagingBean;

/**
 * Created by jianl on 19/05/2017.
 */
public class YahooVendorActionMessage extends MessagingBean {

    private String action;
    private String securityCode;
    private String specificCode;
    public YahooVendorActionMessage() {
        super("yahoo.vendor.action");
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSpecificCode() {
        return specificCode;
    }

    public void setSpecificCode(String specificCode) {
        this.specificCode = specificCode;
    }

    @Override
    public String toString() {
        return "YahooVendorActionMessage{" +
                "action='" + action + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", specificCode='" + specificCode + '\'' +
                '}';
    }
}
