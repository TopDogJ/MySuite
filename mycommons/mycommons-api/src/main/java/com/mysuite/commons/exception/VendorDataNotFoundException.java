package com.mysuite.commons.exception;

/**
 * Created by jianl on 19/04/2017.
 */
public class VendorDataNotFoundException extends Throwable{
    public VendorDataNotFoundException(String message) {
        super(message);
    }
    public VendorDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
