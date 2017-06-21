package com.mysuite.commons.exception.service;

/**
 * Created by jianl on 28/04/2017.
 */
public class HttpServiceException extends ServiceException {
    public HttpServiceException(String message) {
        super(message);
    }

    public HttpServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
