package com.loujie.www.util.http;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUrlUtils {

    private static final int timeout = 5000;

    public static class GET {

        public static String run(String httpurl) {
            return run(httpurl, null, null, null);
        }

        public static String run(String httpurl, Map<String, Object> params) {
            return run(httpurl, params, null, null);
        }

        public static String run(String httpurl, String headName, String headValue) {
            return run(httpurl, null, headName, headValue);
        }

        public static String run(String httpurl, Map<String, Object> params, String headName, String headValue) {
            HttpURLConnection connection = null;
            InputStream is = null;
            BufferedReader br = null;
            String result = null;// 返回结果字符串
            try {
                httpurl = UrlUrlUtils.getUrl(httpurl, params);
                // 创建远程url连接对象
                URL url = new URL(httpurl);
                connection = (HttpURLConnection) url.openConnection();

                // 设置连接方式:GET
                connection.setRequestMethod("GET");
                //Get请求不需要DoOutPut
                connection.setDoOutput(false);
                connection.setDoInput(true);
                // 设置连接主机服务器的超时时间：15000毫秒
                connection.setConnectTimeout(timeout);
                // 设置读取远程返回的数据时间：60000毫秒
                connection.setReadTimeout(timeout);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                if (headName != null && headValue != null && !headName.isEmpty() && !headValue.isEmpty()) {
                    connection.setRequestProperty(headName, headValue);
                }

                // 发送请求
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    // 封装输入流is，并指定字符集
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    // 存放数据
                    StringBuffer sbf = new StringBuffer();
                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append("\r\n");
                    }
                    result = sbf.reverse().delete(0, 2).reverse().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 关闭资源
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != connection) {
                    try {
                        connection.disconnect();// 关闭远程连接
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

    }

    public static class POST {

        public static String run(String url) {
            return run(url, null, null, null, null);
        }

        public static String run(String url, Map<String, Object> params) {
            return run(url, params, null, null, null);
        }

        public static String run(String url, String jsonString) {
            return run(url, null, jsonString, null, null);
        }

        public static String run(String url, Map<String, Object> params, String jsonString) {
            return run(url, params, jsonString, null, null);
        }

        public static String run(String httpurl, Map<String, Object> params, String jsonString, String headName, String headValue) {
            HttpURLConnection connection = null;
            InputStream is = null;
            BufferedReader br = null;
            String result = null;// 返回结果字符串
            try {
                if (jsonString != null && !jsonString.isEmpty()) {
                    httpurl = UrlUrlUtils.getUrl(httpurl, params);
                }
                // 创建远程url连接对象
                URL url = new URL(httpurl);
                connection = (HttpURLConnection) url.openConnection();

                // 设置连接方式:GET
                connection.setRequestMethod("POST");
                //Get请求不需要DoOutPut
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 设置连接主机服务器的超时时间：15000毫秒
                connection.setConnectTimeout(timeout);
                // 设置读取远程返回的数据时间：60000毫秒
                connection.setReadTimeout(timeout);
                String param = null;
                if (jsonString != null && !jsonString.isEmpty()) {
                    connection.setRequestProperty("Content-Type", "application/json");
                    param = jsonString;
                } else {
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    param = UrlParamUtils.getParam(params);
                }
                if (headName != null && headValue != null && !headName.isEmpty() && !headValue.isEmpty()) {
                    connection.setRequestProperty(headName, headValue);
                }
                // 发送请求
                connection.connect();

                if (param != null && !param.isEmpty()) {
                    DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                    dos.writeBytes(param);
                    dos.flush();
                    dos.close();
                }

                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    // 封装输入流is，并指定字符集
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    // 存放数据
                    StringBuffer sbf = new StringBuffer();
                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append("\r\n");
                    }
                    result = sbf.reverse().delete(0, 2).reverse().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 关闭资源
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != connection) {
                    try {
                        connection.disconnect();// 关闭远程连接
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
    }

    public static class UrlParamUtils {

        /**
         * 获取参数字符串,用&连接起来的字符串
         *
         * @param params
         * @return
         */
        public static String getParam(Map<String, Object> params) {
            String returnResult = "";
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    returnResult += "&" + entry.getKey() + "=" + encodingValue(entry.getValue());
                }
                returnResult = returnResult.replaceFirst("&", "");
            }
            return returnResult;
        }

        /**
         * 对值进行UrlEncoder(对值进行Url编码)
         *
         * @param value
         * @return
         */
        public static String encodingValue(Object value) {
            String returnResult = "";
            if (value != null && !value.toString().isEmpty()) {
                try {
                    returnResult = URLEncoder.encode(value.toString(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return returnResult;
        }
    }

    public static class UrlUrlUtils {

        /**
         * 获取新的url,根据params的值进行url拼装
         *
         * @param url
         * @param params
         * @return
         */
        public static String getUrl(String url, Map<String, Object> params) {
            String getParam = UrlParamUtils.getParam(params);
            if (getParam != null && !getParam.isEmpty()) {
                if (url.contains("?")) {
                    url += url + "&" + getParam;
                } else {
                    url += url + "?" + getParam;
                }
            }
            return url;
        }
    }

}
