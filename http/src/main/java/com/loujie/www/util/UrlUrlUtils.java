package com.loujie.www.util;

import java.util.Map;

/**
 * @name loujie
 * @date 2019/1/30
 */
public class UrlUrlUtils {

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
