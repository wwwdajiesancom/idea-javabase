package com.loujie.www.pay.wx.protocol.unifiedorder;

import com.loujie.www.pay.wx.config.WxConfigure;
import com.loujie.www.pay.wx.protocol.base.ToMapReqData;
import com.loujie.www.pay.wx.util.RandomStringGenerator;
import com.loujie.www.pay.wx.util.Signature;
import com.loujie.www.util.common.ArgsUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Date;

/**
 * 统一支付
 *
 * @author loujie
 */
@XStreamAlias("xml")
public class UnifiedorderReqData extends ToMapReqData {

    private static final long serialVersionUID = 6626515408033673872L;

    private String appid;// 公众账号ID,(32)必填
    private String mch_id;// 商户号,(32)必填
    private String device_info = "WEB";// 设备号,因为是PC端的,所以传WEB
    private String nonce_str;// 随机字符串,(32),必填
    private String sign;// 签名,(32)

    /**
     * start** 订单信息 **end
     **/

    private String out_trade_no;// 商户订单号,(32)必填
    private String body;// 商品描述,(128),必填,需要特定的命名规则,[浏览器打开的网站主页title名
    // -商品概述]必填,[京东商城订单]
    private String fee_type = "CNY";// 货币类型,默认人民币
    private String total_fee;// 订单总金额,单位:分,必填

    // 下面是关联参数
    private String trade_type = "NATIVE";
    private String product_id;// 商品ID
    private String spbill_create_ip;// 终端Ip

    /**
     * start** 订单信息 **end
     **/

    private String notify_url;// 异步同通知url,必填

    /**
     * start** 可选扩展参数 **end
     **/

    private String openid;// trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
    private String attach;// 附加数据,127,可以标记是那种产品,那个平台上的
    private String time_start;// 交易起始时间,format:yyyyMMddHHmmss
    private String time_expire;// 交易结束时间,yyyyMMddHHmmss,最短失效时间间隔必须大于5分钟

    /**
     * start** 可选扩展参数 **end
     **/

    public UnifiedorderReqData pc(String out_trade_no, String body, String total_fee, String product_id, String notify_url,
                                  String spbill_create_ip, String attach, String time_start, String time_expire, WxConfigure wxConfigure) {
        this.initConfig(out_trade_no, body, total_fee, product_id, notify_url, spbill_create_ip, attach, time_start, time_expire, "NATIVE", null, wxConfigure);
        return this;
    }

    public UnifiedorderReqData h5(String out_trade_no, String body, String total_fee, String product_id, String notify_url,
                                  String spbill_create_ip, String attach, String time_start, String time_expire, WxConfigure wxConfigure) {
        this.initConfig(out_trade_no, body, total_fee, product_id, notify_url, spbill_create_ip, attach, time_start, time_expire, "MWEB", null, wxConfigure);
        return this;
    }

    public UnifiedorderReqData jsapi(String out_trade_no, String body, String total_fee, String product_id, String notify_url,
                                     String spbill_create_ip, String attach, String time_start, String time_expire, String openid, WxConfigure wxConfigure) {
        this.initConfig(out_trade_no, body, total_fee, product_id, notify_url, spbill_create_ip, attach, time_start, time_expire, "JSAPI", openid, wxConfigure);
        return this;
    }

    /**
     * 初始化配置
     *
     * @param out_trade_no     商户的订单号,必填[必须是唯一的]
     * @param body             商品描述,必填[需要特定的命名规则]
     * @param total_fee        订单金额,分,必填
     * @param product_id       产品Id,必填,自定义但需要唯一
     * @param notify_url       异步通知地址,必填
     * @param spbill_create_ip 终端Ip地址,即服务器的Ip地址,必填
     * @param attach           附加数据,可以明确的标记产品或者产品的出处,可选
     * @param time_start       交易的起始时间,可选
     * @param time_expire      交易的结束时间,可选
     * @param trade_type       交易类型：扫码=NATIVE,H5=MWEB
     * @param wxConfigure      相关配置
     */
    private void initConfig(String out_trade_no, String body, String total_fee, String product_id, String notify_url,
                            String spbill_create_ip, String attach, String time_start, String time_expire, String trade_type, String openid, WxConfigure wxConfigure) {
        // 微信分配的公众号ID（开通公众号之后可以获取到）
        this.setAppid(wxConfigure.getAppid());
        // 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        this.setMch_id(wxConfigure.getMchid());
        // 随机字符串
        this.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
        // 设置Ip地址
        this.setSpbill_create_ip(spbill_create_ip);// WebUtils.getIpAddr(request)

        this.out_trade_no = out_trade_no;
        this.body = body;
        this.total_fee = total_fee;
        this.product_id = product_id;
        this.notify_url = notify_url;
        this.trade_type = trade_type;

        this.openid = openid;
        this.attach = attach;
        this.time_start = time_start;
        Date timeStartDate = ArgsUtils.parseDate(time_start, "yyyyMMddHHmmss");
        Date timeExpireDate = ArgsUtils.parseDate(time_expire, "yyyyMMddHHmmss");
        if ((timeExpireDate.getTime() - timeStartDate.getTime()) / (1000 * 60) < 15) {
            this.time_expire = ArgsUtils.formatDate(ArgsUtils.getDateMinute(timeStartDate, 16), "yyyyMMddHHmmss");
        } else {
            this.time_expire = ArgsUtils.formatDate(ArgsUtils.getDateMinute(timeExpireDate, 16), "yyyyMMddHHmmss");
        }

        // 根据API给的签名规则进行签名,它必须放到最后面
        String sign_ = Signature.getSign(this.toMap(), wxConfigure);
        this.setSign(sign_);
    }

    public UnifiedorderReqData() {
        super();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

}
