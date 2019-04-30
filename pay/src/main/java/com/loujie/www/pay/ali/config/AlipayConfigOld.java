package com.loujie.www.pay.ali.config;


import com.loujie.www.util.common.ArgsUtils;
import com.loujie.www.util.properties.PropertiesUtils;

/**
 * 即时到账需要的配置信息
 *
 * @name loujie
 * @date 2019/4/19
 */
public class AlipayConfigOld {

    // 即时到账,这个是ali早期推出的版本接口,它只适用于pc端的微信扫码支付；后期推出的新api也包含了这个功能,详细的请看AlipayConfigNew中的配置
    // 即使到账,文档,https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.VdnuoC&treeId=62&articleId=104746&docType=1

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 支付宝网关
    public final String aliGateway = "https://openapi.alipay.com/gateway.do";//
    // 字符编码格式 目前支持 gbk 或 utf-8
    public final String input_charset = "utf-8";
    // 签名类型
    public final String sign_type = "RSA";
    // 调用的接口名，无需修改
    public final String service = "create_direct_pay_by_user";
    // 支付类型 ，无需修改
    public final String payment_type = "1";
    public final String format_json = "json";
    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    // ↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 防钓鱼时间戳 若要使用请调用类文件submit中的query_timestamp函数
    // public String anti_phishing_key = "";

    // 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
    // public String exter_invoke_ip = "";

    // ↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    // 动态参数
    // 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串
    public String partner;
    // 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
    public String seller_id;
    // 公钥
    public String public_key;
    // 私钥
    public String private_key;
    // 回调地址
    public String notify_url;
    // 成功后的跳转页面
    public String return_url;

    public AlipayConfigOld init() {
        return this.init(PropertiesUtils.getProperty("ali_web_partner"), PropertiesUtils.getProperty("ali_web_public_key"), PropertiesUtils.getProperty("ali_web_private_key"), PropertiesUtils.getProperty("alipay_notify_url"), PropertiesUtils.getProperty("alipay_return_url"));
    }

    public AlipayConfigOld init(String partner, String publicKey, String privateKey, String notify_url, String return_url) {
        this.partner = partner;
        this.seller_id = this.partner;
        this.public_key = publicKey;
        this.private_key = privateKey;
        this.notify_url = notify_url;
        this.return_url = return_url;
        return this;
    }

    public AlipayConfigOld() {
        this.init();
    }

    public void setReturn_url(String return_url) {
        if (!ArgsUtils.isEmpty(return_url))
            this.return_url = return_url;
    }
}
