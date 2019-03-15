package com.loujie.www.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.loujie.www.util.common.ArgsUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;


public class HttpUtils {
    private static final int MAX_TIMEOUT = 2000;
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * Http请求
     *
     * @param url http_url地址
     * @return
     */
    public static String doHttpGet(String url, String charset) {
        return doHttpGet(url, new HashMap<String, Object>(), charset);
    }

    /**
     * 带参数的Http请求
     *
     * @param url    http_url地址
     * @param params 参数列表
     * @return
     */
    public static String doHttpGet(String url, Map<String, Object> params, String charset) {
        // 1.创建Get请求
        HttpGet get = new HttpGet(url + Http.bulidParamsStr(params));
        // 2.执行请求
        HttpResponse httpResponse = Http.execute(get);
        // 3.获取结果，并返回
        return Http.getResponseResult(httpResponse, charset);
    }

    /**
     * post请求,无参数
     *
     * @param url
     * @return
     */
    public static String doHttpPost(String url, String charset) {
        return doHttpPost(url, new HashMap<String, Object>(), charset);
    }

    /**
     * post请求
     *
     * @param url
     * @param params 参数组
     * @return
     */
    public static String doHttpPost(String url, Map<String, Object> params, String charset) {
        // 1.创建Post请求
        HttpPost post = new HttpPost(url);
        post.setConfig(Http.RC.requestConfig);
        // 2.设置参数
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            post.setEntity(
                    new UrlEncodedFormEntity(pairList, Charset.forName(charset == null ? DEFAULT_CHARSET : charset)));
        }
        // 3.执行
        HttpResponse httpResponse = Http.execute(post);
        // 4.获取结果
        String result = Http.getResponseResult(httpResponse, charset);
        if (httpResponse != null) {
            try {
                EntityUtils.consume(httpResponse.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * post请求,传递的是json对象
     *
     * @param url
     * @param jsonBody
     * @return
     */
    public static String doHttpPostJson(String url, String jsonBody, String charset) {
        // 1.创建请求
        HttpPost post = new HttpPost(url);
        post.setConfig(Http.RC.requestConfig);
        // 2.设置参数及属性
        if (jsonBody != null && !jsonBody.isEmpty()) {
            StringEntity stringEntity = new StringEntity(jsonBody.toString(),
                    charset == null ? DEFAULT_CHARSET : charset);// 解决中文乱码问题
            stringEntity.setContentEncoding(charset == null ? DEFAULT_CHARSET : charset);
            stringEntity.setContentType("application/json");
            post.setEntity(stringEntity);
        }
        // 3.执行
        HttpResponse httpResponse = Http.execute(post);
        // 4.获取结果
        String result = Http.getResponseResult(httpResponse, charset);
        if (httpResponse != null) {
            try {
                EntityUtils.consume(httpResponse.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * post请求,传递的是json对象
     *
     * @param url
     * @param jsonBody
     * @return
     */
    public static String doHttpsPostJson(String url, String jsonBody, String charset) {
        // 1.创建请求
        HttpPost post = new HttpPost(url);
        post.setConfig(Http.RC.requestConfig);
        // 2.设置参数及属性
        if (jsonBody != null && !jsonBody.isEmpty()) {
            StringEntity stringEntity = new StringEntity(jsonBody.toString(),
                    charset == null ? DEFAULT_CHARSET : charset);// 解决中文乱码问题
            stringEntity.setContentEncoding(charset == null ? DEFAULT_CHARSET : charset);
            stringEntity.setContentType("application/json");
            post.setEntity(stringEntity);
        }
        // 3.执行
        HttpResponse httpResponse = Http.execute_https(post);
        // 4.获取结果
        String result = Http.getResponseResult(httpResponse, charset);
        if (httpResponse != null) {
            try {
                EntityUtils.consume(httpResponse.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Https请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doHttpsPost(String url, Map<String, Object> params, String charset) {
        // 1.创建Post请求
        HttpPost post = new HttpPost(url);
        post.setConfig(Http.RC.requestConfig);
        // 2.设置参数
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            post.setEntity(
                    new UrlEncodedFormEntity(pairList, Charset.forName(charset == null ? DEFAULT_CHARSET : charset)));
        }
        // 3.执行
        HttpResponse httpResponse = Http.execute_https(post);
        // 4.获取结果
        String result = Http.getResponseResult(httpResponse, charset);
        if (httpResponse != null) {
            try {
                EntityUtils.consume(httpResponse.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String postData(String urlStr, String data, String contentType) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            if (contentType != null)
                conn.setRequestProperty("content-type", contentType);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            if (data == null)
                data = "";
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
            //logger.error("Error connecting to " + urlStr + ": " + e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * 基础调用,不可见
     *
     * @author loujie
     */
    static class Http {

        /**
         * 构建参数地址
         *
         * @param params
         * @return
         */
        static String bulidParamsStr(Map<String, Object> params, String... charsets) {
            if (charsets == null || charsets.length == 0) {
                charsets = new String[]{DEFAULT_CHARSET};
            }
            StringBuilder stringBuilder = new StringBuilder("?");
            // 1.拼接
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getKey() != null && !entry.getKey().isEmpty()) {
                        try {
                            stringBuilder.//
                                    append(entry.getKey()).//
                                    append("=").//
                                    append(URLEncoder.encode(ArgsUtils.parseString(entry.getValue()), charsets[0])).//
                                    append("&");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            // 2.移除最后一个字符
            stringBuilder.reverse().deleteCharAt(0).reverse();
            // 3.返回结果
            // url地址编码
            return stringBuilder.toString();
        }

        /**
         * 执行请求
         *
         * @param httpRequestBase
         * @return
         */
        static HttpResponse execute(HttpRequestBase httpRequestBase) {
            return execute(httpRequestBase, HttpClients.createDefault());
        }

        /**
         * 执行请求
         *
         * @param httpRequestBase
         * @return
         */
        static HttpResponse execute_https(HttpRequestBase httpRequestBase) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            httpClientBuilder.setSSLContext(SSLContexts.createDefault());
            return execute(httpRequestBase, httpClientBuilder.build());
        }

        /**
         * 执行请求
         *
         * @return
         */
        static HttpResponse execute(HttpRequestBase httpRequestBase, HttpClient httpClient) {
            HttpResponse response = null;
            // 2.构造HttpClient
            try {
                response = httpClient.execute(httpRequestBase);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * 处理结果
         *
         * @param httpResponse
         * @return
         */
        static String getResponseResult(HttpResponse httpResponse, String charset) {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                try {
                    return EntityUtils.toString(entity, Charset.forName(charset == null ? DEFAULT_CHARSET : charset));
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        static class RC {
            static RequestConfig requestConfig;

            static {
                RequestConfig.Builder configBuilder = RequestConfig.custom();
                // 设置连接超时
                configBuilder.setConnectTimeout(MAX_TIMEOUT);
                // 设置读取超时
                configBuilder.setSocketTimeout(MAX_TIMEOUT);
                // 设置从连接池获取连接实例的超时
                configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
                requestConfig = configBuilder.build();
            }
        }

    }

}