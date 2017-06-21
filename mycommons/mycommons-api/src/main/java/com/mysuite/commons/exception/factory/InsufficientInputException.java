package com.mysuite.commons.exception.factory;

/**
 * Created by jianl on 12/05/2017.
 */
public class InsufficientInputException extends Throwable{
    public InsufficientInputException() {
    }

    public InsufficientInputException(String message) {
        super(message);
    }

    public InsufficientInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientInputException(Throwable cause) {
        super(cause);
    }

    public InsufficientInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
