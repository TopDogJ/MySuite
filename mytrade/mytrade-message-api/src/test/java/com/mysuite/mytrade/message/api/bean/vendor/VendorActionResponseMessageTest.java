package com.mysuite.mytrade.message.api.bean.vendor;

import junit.framework.TestCase;

/**
 * Created by jianl on 25/05/2017.
 */
public class VendorActionResponseMessageTest extends TestCase {

    public void testJsonSerialise() {
        VendorActionResponseMessage vendorActionResponseMessage = new VendorActionResponseMessage();
        vendorActionResponseMessage.setData("");
    }
}