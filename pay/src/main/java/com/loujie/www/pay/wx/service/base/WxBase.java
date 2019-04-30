package com.loujie.www.pay.wx.service.base;



import com.loujie.www.pay.wx.config.WxConfigure;
import com.loujie.www.pay.wx.util.HttpsRequest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * User: rizenguo Date: 2014/12/10 Time: 15:44 服务的基类
 */
public class WxBase {

    // API的地址
    private String apiURL;

    // 发请求的HTTPS请求器
    private IWxRequest serviceRequest;

    public WxBase(String api, WxConfigure wxConfigure)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnrecoverableKeyException,
            KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        apiURL = api;
        serviceRequest = new HttpsRequest(wxConfigure);
    }

    protected String sendPost(Object xmlObj) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {
        return serviceRequest.sendPost(apiURL, xmlObj);
    }

    /**
     * 供商户想自定义自己的HTTP请求器用
     *
     * @param request 实现了IserviceRequest接口的HttpsRequest
     */
    public void setServiceRequest(IWxRequest request) {
        serviceRequest = request;
    }
}
