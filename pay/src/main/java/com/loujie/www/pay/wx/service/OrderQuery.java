package com.loujie.www.pay.wx.service;

import com.loujie.www.pay.wx.config.WxConfigure;
import com.loujie.www.pay.wx.config.WxUrl;
import com.loujie.www.pay.wx.protocol.orderquery.OrderQueryReqData;
import com.loujie.www.pay.wx.service.base.WxBase;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;


public class OrderQuery extends WxBase {

    public OrderQuery(WxConfigure wxConfigure)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnrecoverableKeyException,
            KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        super(WxUrl.PAY_QUERY_API, wxConfigure);
    }

    private OrderQuery(String api, WxConfigure wxConfigure)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnrecoverableKeyException,
            KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        super(api, wxConfigure);
    }

    /**
     * 订单查询
     *
     * @param orderQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String request(OrderQueryReqData orderQueryReqData) throws Exception {
        String responseString = sendPost(orderQueryReqData);
        return responseString;
    }

}
