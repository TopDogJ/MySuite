package com.mysuite.mytrade.message.api.bean.vendor;

import com.mysuite.mytrade.message.api.bean.MessagingBean;

/**
 * Created by jianl on 25/05/2017.
 */
public class VendorActionResponseMessage extends MessagingBean {

    private VendorActionResponseType vendorActionResponseType;
    private String Data;
    private String status;

    public VendorActionResponseMessage() {
        super("vendor.response");
    }


    public VendorActionResponseType getVendorActionResponseType() {
        return vendorActionResponseType;
    }

    public void setVendorActionResponseType(VendorActionResponseType vendorActionResponseType) {
        this.vendorActionResponseType = vendorActionResponseType;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VendorActionResponseMessage{" +
                "vendorActionResponseType=" + vendorActionResponseType +
                ", Data='" + Data + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
