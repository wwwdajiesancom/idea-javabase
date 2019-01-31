package com.loujie.www.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 处理Url参数的类
 *
 * @name loujie
 * @date 2019/1/30
 */
public class UrlParamUtils {

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
