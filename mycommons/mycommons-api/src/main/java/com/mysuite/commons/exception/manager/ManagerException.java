package com.mysuite.commons.exception.manager;

/**
 * Created by jianl on 30/04/2017.
 */
public class ManagerException extends Throwable {
    public ManagerException() {
    }

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(Throwable cause) {
        super(cause);
    }

    public ManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
