package com.mysuite.mytrade.engine.generator;

import com.mysuite.mytrade.api.entity.bean.security.Security;
import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;

/**
 * Created by jianl on 20/05/2017.
 */
public interface SecurityMessageGenerator {
    public SecurityMessage generateStandard(final Security security);
}
