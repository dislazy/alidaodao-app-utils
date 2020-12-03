package com.alidaodao.app.http;

import com.alibaba.fastjson.JSON;
import com.alidaodao.app.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;



public class HttpRequest {

    private static Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    /**
     * 全局配置
     */
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();

    private static PoolingHttpClientConnectionManager cm = null;

    static {
        cm = new PoolingHttpClientConnectionManager(30000, TimeUnit.MILLISECONDS);
        cm.setMaxTotal(500);
        cm.setDefaultMaxPerRoute(300);
    }



    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }


    public static String httpGet(String url, Map<String, String> pmsMap, Map<String, String> headMap) {
        if (url == null) {
            return null;
        }
        if (pmsMap != null) {
            url = url + "?" + StringUtils.paramToQueryString(pmsMap, "UTF-8");
        }
        CloseableHttpResponse httpResponse = null;
        //  创建默认的 httpClient 实例
        CloseableHttpClient httpClient = HttpRequest.getHttpClient();
        //  发送 get 请求
        try {
            HttpGet httpGet = new HttpGet(url);
            if (headMap != null) {
                for (Map.Entry<String, String> entry : headMap.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            httpResponse = httpClient.execute(httpGet);
            // response 实体
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity) {
                String response = EntityUtils.toString(entity);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    return response;
                }
            }
            logger.info("[HttpRequest][httpGet][request][fail],url:[{}],result:[{}]", url, JSON.toJSONString(entity));
            return null;
        } catch (Exception e) {
            logger.error("[HttpRequest][httpGet][request][error],url:[{}]", url, e);
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                } catch (Exception e) {
                    logger.error("[HttpRequest][close][fail],url:[{}]", url, e);
                }
            }
        }
        return null;
    }
}
