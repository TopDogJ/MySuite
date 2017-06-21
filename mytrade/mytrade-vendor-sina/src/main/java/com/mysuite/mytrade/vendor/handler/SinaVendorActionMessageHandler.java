package com.mysuite.mytrade.vendor.handler;

import com.mysuite.mytrade.message.api.bean.vendor.sina.SinaVendorActionRequestMessage;

/**
 * Created by jianl on 15/05/2017.
 */
public interface SinaVendorActionMessageHandler {
    public void handleMessage(final SinaVendorActionRequestMessage sinaVendorActionMessage);
}
