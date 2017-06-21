package com.mysuite.mytrade.vendor.service.http;

import com.mysuite.commons.exception.service.HttpServiceException;
import com.mysuite.commons.log.AbstractLoggable;
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
public class HttpServiceImpl extends AbstractLoggable implements HttpService {

    @Autowired
    private CloseableHttpClient httpClient;

    public HttpServiceImpl() {
        super(LogFactory.getLog(HttpServiceImpl.class));
    }

    public String doGetDownloadFile(String url, String filename) {
        HttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("Cookie",  "B=7jcehopce6cns&b=3&s=in; PRF=t%3D600000.SS");
        httpGet.addHeader("Referer","https://finance.yahoo.com/quote/600000.SS/history?p=600000.SS");
        httpGet.addHeader("Host", "query1.finance.yahoo.com");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
        httpGet.addHeader("Accept","text/html, application/xhtml+xml, image/jxr, */*");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate");
        httpGet.addHeader("Accept-Language", "en-NZ, en; q=0.8, zh-Hans-CN; q=0.5, zh-Hans; q=0");

        String responseCode = null;
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
                return String.valueOf(response.getStatusLine().getStatusCode());
            } else if (response.getStatusLine().getStatusCode() == 404) {
                this.getLogger().warn("No file found on url <" + url + ">. Status code returned 404. Abort download");
                return String.valueOf(response.getStatusLine().getStatusCode());
            }
        } catch (Throwable e) {
            this.getLogger().fatal("Exception occured whilist download file from url <" + url + ">. Result returned false for retry.");
            responseCode = "500";
        }
        return responseCode;
    }
}
