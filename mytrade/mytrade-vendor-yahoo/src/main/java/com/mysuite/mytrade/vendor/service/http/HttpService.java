package com.mysuite.mytrade.vendor.service.http;

import com.mysuite.commons.exception.service.HttpServiceException;

import java.util.Map;

/**
 * Created by jianl on 14/04/2017.
 */
public interface HttpService {
    public String doGetDownloadFile(String url, String filename) throws HttpServiceException;
}
