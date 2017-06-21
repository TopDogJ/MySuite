package com.mysuite.commons.exception.entity;

/**
 * Created by jianl on 6/04/2017.
 */
public class EntityNotFoundException extends Throwable {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
