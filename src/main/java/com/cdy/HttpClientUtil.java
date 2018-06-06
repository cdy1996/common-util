package com.cdy;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * http请求工具类
 * Created by 陈东一
 * 2018/5/20 14:38
 */
public class HttpClientUtil {
    
    private static String defaultCharset = "utf-8";
    
    
    /**
     * 发送get请求
     * @param url String
     * @return String
     * @throws IOException
     */
    public static String doGet(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(get)) {
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity, defaultCharset);
            System.out.println(string);
            return string;
        }
    }
    
    /**
     * 发送post请求
     * @param url String
     * @param parameter Map<String, String>  参数key和value
     * @return String
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> parameter) throws IOException {
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> list = parameter.entrySet().stream().map(e -> new BasicNameValuePair(e.getKey(), e.getValue())).collect(Collectors.toList());
        if(list.size() > 0){
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, defaultCharset);
            post.setEntity(entity);
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity, defaultCharset);
            System.out.println(string);
            return string;
        }
    }
}
