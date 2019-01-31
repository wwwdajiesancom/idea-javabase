package com.loujie.www.okhttp;

import com.loujie.www.util.UrlUrlUtils;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonWriter;
import okhttp3.*;
import okio.BufferedSink;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp
 *
 * @name loujie
 * @date 2019/1/29
 */
public class OkHttpUtils {
    private static long timeout = 5;

    private static OkHttpClient client = null;

    static {
        client = new OkHttpClient
                .Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }

    public static void init() {
    }

    public static class GET {

        public static String run(String url, Map<String, Object> params) {
            return run(url, params, null, null);
        }

        public static String run(String url) {
            return run(url, null, null, null);
        }

        public static String run(String url, String headName, String headValue) {
            return run(url, null, headName, headValue);
        }

        public static String run(String url, Map<String, Object> params, String headName, String headValue) {
            try {
                Request.Builder rb = new Request.Builder().get().url(UrlUrlUtils.getUrl(url, params));
                if (headName != null && headValue != null && !headValue.isEmpty() && !headName.isEmpty()) {
                    rb.addHeader(headName, headValue);
                }
                return _Response.getBody(_Response.execute(rb.build()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class POST {

        public static String run(String url, Map<String, Object> params, String jsonString) {
            return run(url, params, jsonString, null, null);
        }

        public static String run(String url, String jsonString) {
            return run(url, null, jsonString, null, null);
        }

        public static String run(String url, Map<String, Object> params) {
            return run(url, params, null, null, null);
        }

        public static String run(String url) {
            return run(url, null, null, null, null);
        }

        public static String run(String url, Map<String, Object> params, String jsonString, String headName, String headValue) {
            try {
                FormBody.Builder formbody = new FormBody.Builder();
                RequestBody requestBody = null;
                if (jsonString != null && !jsonString.isEmpty()) {
                    requestBody = formbody.build().create(MediaType.parse("application/json;charset=utf-8"), jsonString);
                    url = UrlUrlUtils.getUrl(url, params);
                } else if (params != null && !params.isEmpty()) {
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        String key = entry.getKey();
                        String value = null;
                        if (entry.getValue() != null) {
                            value = entry.getValue().toString();
                        }
                        if (key == null || value == null) continue;
                        formbody.add(key, value);
                    }
                    requestBody = formbody.build();
                } else {
                    requestBody = formbody.build();
                }
                Request.Builder rb = new Request.Builder().post(requestBody).url(url);
                if (headName != null && headValue != null && !headName.isEmpty() && !headValue.isEmpty()) {
                    rb.addHeader(headName, headValue);
                }
                return _Response.getBody(_Response.execute(rb.build()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    static class _Response {

        static Response execute(Request request) {
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        static String getBody(Response response) {
            if (response != null) {
                try {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

}
