package com.loujie.www.pay.wx.protocol.unifiedorder;


import com.loujie.www.pay.wx.protocol.base.BaseResData;

/**
 * 扫码支付结果
 *
 * @author loujie
 */
public class UnifiedorderResData extends BaseResData {

    private static final long serialVersionUID = 5115444776601319800L;

    /**
     * start** 以下字段在return_code为SUCCESS的时候有返回 **start
     **/

    public String device_info;// 设备号

    /** end** 以下字段在return_code为SUCCESS的时候有返回 **end **/

    /**
     * start** 以下字段在return_code 和result_code都为SUCCESS的时候有返回 **start
     **/

    public String trade_type;// 交易类型

    public String prepay_id;// 预支付交易会话标识

    public String code_url;// 二维码连接

    public String mweb_url;// mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。

    @Override
    public String toString() {
        return "UnifiedorderResData{" +
                "device_info='" + device_info + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", code_url='" + code_url + '\'' +
                ", mweb_url='" + mweb_url + '\'' +
                ", return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", result_code='" + result_code + '\'' +
                ", err_code='" + err_code + '\'' +
                ", err_code_des='" + err_code_des + '\'' +
                ", appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    /**
     * end** 以下字段在return_code 和result_code都为SUCCESS的时候有返回**end
     **/


    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public String getMweb_url() {
        return mweb_url;
    }

    public void setMweb_url(String mweb_url) {
        this.mweb_url = mweb_url;
    }
}
