package com.loujie.www.pay.wx.config;


import com.loujie.www.pay.wx.util.HttpsRequest;
import com.loujie.www.util.properties.PropertiesUtils;

/**
 * 这里放置各种配置数据
 *
 * @author loujie
 */
public class WxConfigure {
    // 这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
    private String key1;// "668edf187e814de69d50fee7bb0cf3b1";
    // 微信分配的公众号ID（开通公众号之后可以获取到）
    private String appID1;// "wxc3d35c8fd5371d80";
    // 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    private String mchID1;// "1399552902";
    // 受理模式下给子商户分配的子商户号
    private String subMchID1 = "";
    // HTTPS证书的本地路径
    private String certLocalPath1 = null;// WxConfigure.class.getClassLoader().getResource("pbs_cert.p12").getPath();
    // HTTPS证书密码，默认密码等于商户号MCHID
    private String certPassword1;// WxConfigure.mchID;
    // 是否使用异步线程的方式来上报API测速，默认为异步模式
    private static boolean useThreadToDoReport1 = true;
    // 机器IP
    private String ip1 = "";

    public WxConfigure init() {
        return this.init(PropertiesUtils.getProperty("wx_key"), PropertiesUtils.getProperty("wx_appID"), PropertiesUtils.getProperty("wx_mchID"), PropertiesUtils.getProperty("wx_certPassword"));
    }

    public WxConfigure init(String wx_key, String wx_appID, String wx_mchID, String wx_certPassword) {
        this.key1 = wx_key;
        this.appID1 = wx_appID;
        this.mchID1 = wx_mchID;
        this.certPassword1 = wx_certPassword;
        return this;
    }

    public WxConfigure() {
        this.init();
    }

    public static String HttpsRequestClassName = HttpsRequest.class.getName();

    public void setIp(String ip) {
        this.ip1 = ip;
    }

    public String getKey() {
        return key1;
    }

    public String getAppid() {
        return appID1;
    }

    public String getMchid() {
        return mchID1;
    }

    public String getSubMchid() {
        return subMchID1;
    }

    public String getCertLocalPath() {
        return certLocalPath1;
    }

    public String getCertPassword() {
        return certPassword1;
    }

    public String getIP() {
        return ip1;
    }

    public static void setHttpsRequestClassName(String name) {
        HttpsRequestClassName = name;
    }

}
