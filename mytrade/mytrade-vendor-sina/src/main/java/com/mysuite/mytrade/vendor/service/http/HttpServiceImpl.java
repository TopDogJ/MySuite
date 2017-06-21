package com.mysuite.mytrade.vendor.service.http;

import com.mysuite.commons.exception.service.HttpServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class HttpServiceImpl implements HttpService {

    private static final Log LOGGER = LogFactory.getLog(HttpServiceImpl.class);

    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    public boolean doGetDownloadFile(String url, Map<String, String> paramMap, String filename) throws HttpServiceException {
        try {
            URIBuilder builder = new URIBuilder(url);
            for (String s : paramMap.keySet()) {
                builder.addParameter(s, paramMap.get(s));
            }
            return doGetDownloadFile(builder.build().toString(), filename);
        } catch (Throwable e) {
            throw new HttpServiceException("Failed to get from url <" + url + ">.", e);
        }

    }

    public boolean doGetDownloadFile(String url, String filename) {
        HttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);

//        httpGet.setConfig(requestConfig);
        httpGet.addHeader("Cookie", "B=7jcehopce6cns&b=3&s=in; PRF=t%3D600000.SS");
        httpGet.addHeader("Referer", "https://finance.yahoo.com/quote/600000.SS/history?p=600000.SS");
        httpGet.addHeader("Host", "query1.finance.yahoo.com");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
        httpGet.addHeader("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate");
        httpGet.addHeader("Accept-Language", "en-NZ, en; q=0.8, zh-Hans-CN; q=0.5, zh-Hans; q=0");
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                File file = new File(filename);
                file.getParentFile().mkdirs();
                FileOutputStream fileout = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int ch = 0;
                while ((ch = is.read(buffer)) != -1) {
                    fileout.write(buffer, 0, ch);
                }
                is.close();
                fileout.flush();
                fileout.close();
                return true;
            } else if (response.getStatusLine().getStatusCode() == 404) {
                LOGGER.info("No file found on url <" + url + ">. Status code returned 404. Abort download");
                return true;
            }
        } catch (Throwable e) {
            LOGGER.info("Exception occured whilist download file from url <" + url + ">. Result returned false for retry.");
            return false;
        }
        return false;
    }

    public String doGet(String url) throws HttpServiceException {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);

            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "GBK");
            } else {
                return null;
            }
        } catch (Throwable e) {
            throw new HttpServiceException("Failed to get from url <" + url + ">.", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOGGER.error("Failed to close response.", e);
                }
            }
        }
    }

    public String doGet(String url, Map<String, String> paramMap) throws HttpServiceException {
        try {
            URIBuilder builder = new URIBuilder(url);
            for (String s : paramMap.keySet()) {
                builder.addParameter(s, paramMap.get(s));
            }
            String resultUrl = builder.build().toString();
            return doGet(resultUrl);
        } catch (Throwable e) {
            throw new HttpServiceException("Failed to get from url <" + url + ">.", e);
        }
    }


}
