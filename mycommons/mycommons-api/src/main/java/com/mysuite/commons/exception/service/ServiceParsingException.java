package com.mysuite.commons.exception.service;

/**
 * Created by jianl on 14/04/2017.
 */
public class ServiceParsingException extends Throwable {
    public ServiceParsingException(String message) {
        super(message);
    }
    public ServiceParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
