package com.mysuite.mytrade.engine.exporter;

import com.mysuite.mytrade.message.api.bean.security.SecurityMessage;

import java.io.IOException;

/**
 * Created by jianl on 20/05/2017.
 */
public interface SecurityMessageFileExporter {
    public void exportToFile(SecurityMessage securityMessage, String filename) throws IOException;
}
