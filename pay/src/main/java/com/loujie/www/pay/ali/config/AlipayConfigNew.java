package com.loujie.www.pay.ali.config;


import com.loujie.www.util.common.ArgsUtils;
import com.loujie.www.util.properties.PropertiesUtils;

/**
 * 新支付宝接口需要固定参数对象
 *
 * @name loujie
 * @date 2019/4/19
 */
public class AlipayConfigNew {

    // 它是新出的支付宝支付接口,文档:https://docs.open.alipay.com/api_1/alipay.trade.page.pay

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 支付宝网关
    public final String aliGateway = "https://openapi.alipay.com/gateway.do";//
    // 字符编码格式 目前支持 gbk 或 utf-8
    public final String input_charset = "utf-8";
    // 签名类型
    public final String sign_type = "RSA2";
    // 支付类型 ，无需修改
    public final String payment_type = "1";
    public final String format_json = "json";

    // 支付宝分配给开发者的应用ID
    public String appid;
    // 公钥
    public String public_key;
    // 私钥
    public String private_key;
    // 回调地址
    public String notify_url;
    // 成功后的跳转页面
    public String return_url;

    // 可选参数
    public String quit_url;// h5支付，需要；支付宝支付中途退出所跳转的页面地址

    public AlipayConfigNew init() {
        return this.init(PropertiesUtils.getProperty("ali_app_id"), PropertiesUtils.getProperty("ali_public_key"), PropertiesUtils.getProperty("ali_private_key"), PropertiesUtils.getProperty("alipay_notify_url"), PropertiesUtils.getProperty("alipay_h5_return_url"));
    }

    public AlipayConfigNew init(String appid, String publicKey, String privateKey, String notify_url, String return_url) {
        this.appid = appid;
        this.public_key = publicKey;
        this.private_key = privateKey;
        this.notify_url = notify_url;
        this.return_url = return_url;
        return this;
    }

    public AlipayConfigNew() {
        this.init();
    }

    public void setReturn_url(String return_url) {
        if (!ArgsUtils.isEmpty(return_url))
            this.return_url = return_url;
    }

    public void setQuit_url(String quit_url) {
        if (!ArgsUtils.isEmpty(quit_url))
            this.quit_url = quit_url;
    }
}
