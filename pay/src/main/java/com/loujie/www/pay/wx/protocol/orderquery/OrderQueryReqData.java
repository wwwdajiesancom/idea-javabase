package com.loujie.www.pay.wx.protocol.orderquery;


import com.loujie.www.pay.wx.config.WxConfigure;
import com.loujie.www.pay.wx.protocol.base.ToMapReqData;
import com.loujie.www.pay.wx.util.RandomStringGenerator;
import com.loujie.www.pay.wx.util.Signature;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 订单查询所需参数
 *
 * @author loujie
 */
@XStreamAlias("xml")
public class OrderQueryReqData extends ToMapReqData {

    private static final long serialVersionUID = -5703308026547084375L;

    public String appid;// 公众账号ID,(32)必填

    public String mch_id;// 商户号,(32)必填

    public String nonce_str;// 随机字符串,(32),必填

    public String sign;// 签名,(32)

    /**
     * start** 二选一参数 **end
     **/

    public String transaction_id;// 微信订单号

    public String out_trade_no;// 商户订单号,我们目前都是用这

    /**
     * start** 二选一参数 **end
     **/

    public OrderQueryReqData() {
        super();
    }

    /**
     * @param transaction_id 微信订单号,[它们二选一]
     * @param out_trade_no   商户订单号,我们目前都是用这,[它们二选一]
     */
    public OrderQueryReqData(String transaction_id, String out_trade_no, WxConfigure wxConfigure) {
        super();

        // 微信分配的公众号ID（开通公众号之后可以获取到）
        this.setAppid(wxConfigure.getAppid());
        // 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        this.setMch_id(wxConfigure.getMchid());
        // 随机字符串
        this.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        this.transaction_id = transaction_id;
        this.out_trade_no = out_trade_no;

        // 根据API给的签名规则进行签名,它必须放到最后面
        String sign_ = Signature.getSign(this.toMap(), wxConfigure);
        this.setSign(sign_);
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

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

}
