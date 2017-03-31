package com.mysuite.service.internal.logging;

import java.lang.annotation.*;

/**
 * Created by jianl on 30/03/2017.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLogging {
}
