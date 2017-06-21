package com.mysuite.mytrade.vendor.service.http;

import com.mysuite.commons.exception.service.HttpServiceException;

import java.util.Map;

/**
 * Created by jianl on 14/04/2017.
 */
public interface HttpService {
    public String doGet(String url) throws HttpServiceException;

    public String doGet(String url, Map<String, String> paramMap) throws HttpServiceException;

    public boolean doGetDownloadFile(String url, Map<String, String> paramMap, String filename) throws HttpServiceException;

    public boolean doGetDownloadFile(String url, String filename) throws HttpServiceException;
}
