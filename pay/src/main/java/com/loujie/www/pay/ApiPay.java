package com.loujie.www.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.loujie.www.pay.ali.config.AlipayConfigNew;
import com.loujie.www.pay.ali.config.AlipayConfigOld;
import com.loujie.www.pay.exception.ServiceException;
import com.loujie.www.pay.wx.config.WxConfigure;
import com.loujie.www.pay.wx.protocol.unifiedorder.UnifiedorderReqData;
import com.loujie.www.pay.wx.protocol.unifiedorder.UnifiedorderResData;
import com.loujie.www.pay.wx.service.Unifiedorder;
import com.loujie.www.pay.wx.util.Signature;
import com.loujie.www.pay.wx.util.Util;
import com.loujie.www.util.common.ArgsUtils;
import com.loujie.www.util.common.JsonUtils;
import com.loujie.www.util.common.WebUtils;
import com.loujie.www.util.properties.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 支付
 *
 * @author loujie
 */
public class ApiPay implements Serializable {

    private static final long serialVersionUID = 6088589559369393526L;

    public Logger logger = LoggerFactory.getLogger(this.getClass());
    private WxConfigure wxConfigure;
    private AlipayConfigOld alipayConfigOld;
    private AlipayConfigNew alipayConfigNew;

    public ApiPay() {
    }

    public ApiPay initWx(WxConfigure wxConfigure) {
        this.wxConfigure = wxConfigure;
        if (this.wxConfigure == null) {
            wxConfigure = new WxConfigure().init();
        }
        return this;
    }

    public ApiPay initAliOld(AlipayConfigOld alipayConfigOld) {
        this.alipayConfigOld = alipayConfigOld;
        if (this.alipayConfigOld == null) {
            this.alipayConfigOld = new AlipayConfigOld().init();
        }
        return this;
    }

    public ApiPay initAliNew(AlipayConfigNew alipayConfigNew) {
        this.alipayConfigNew = alipayConfigNew;
        if (this.alipayConfigNew == null) {
            this.alipayConfigNew = new AlipayConfigNew().init();
        }
        return this;
    }

    /**
     * 支付宝生成参数
     *
     * @param request
     * @param orderNo    商户订单号,需要定义一个订单策略,必填
     * @param orderName  订单名称,必填
     * @param totalFee   付款金额,分,必填
     * @param desc       商品描述,可填
     * @param timeExpire 超时时间,1m=1分钟,1h=1小时,1d=1天,可填
     * @param payMode    支付模式,0：订单码-简约前置模式，对应iframe宽度不能小于600px，高度不能小于300px；
     *                   1：订单码-前置模式，对应iframe宽度不能小于300px，高度不能小于600px；
     *                   3：订单码-迷你前置模式，对应iframe宽度不能小于75px，高度不能小于75px。
     *                   4：订单码-可定义宽度的嵌入式二维码，商户可根据需要设定二维码的大小。 2：订单码-跳转模式 可选
     * @return
     */
    public Map<String, String> alipayParamOld(HttpServletRequest request, String orderNo, String orderName,
                                              Integer totalFee, String desc, String timeExpire, String payMode, String qrCodeWidth) {
        Map<String, String> paramMapTemp = new HashMap<String, String>();
        paramMapTemp.put("service", this.alipayConfigOld.service);// 是收款操作
        paramMapTemp.put("partner", this.alipayConfigOld.partner);// 合作身份者ID
        paramMapTemp.put("seller_id", this.alipayConfigOld.seller_id);
        paramMapTemp.put("_input_charset", this.alipayConfigOld.input_charset);
        paramMapTemp.put("payment_type", this.alipayConfigOld.payment_type);

        String notifyUrl = this.alipayConfigOld.notify_url;
        if (!ArgsUtils.isEmpty(notifyUrl)) {
            paramMapTemp.put("notify_url", String.format(notifyUrl, orderNo));// 异步通知
        }

        // 支付成功后的地址
        String returnUrl = this.alipayConfigOld.return_url;
        if (!ArgsUtils.isEmpty(returnUrl)) {
            paramMapTemp.put("return_url", returnUrl);// 页面同步跳转地址
        }

        // 防钓鱼
        // paramMapTemp.put("anti_phishing_key",
        // alipayConfigOld.anti_phishing_key);
        // paramMapTemp.put("exter_invoke_ip", alipayConfigOld.exter_invoke_ip);

        if (!ArgsUtils.isEmpty(timeExpire)) {
            paramMapTemp.put("it_b_pay", timeExpire);
        }
        paramMapTemp.put("goods_type", "0");
        if (!ArgsUtils.isEmpty(payMode)) {
            paramMapTemp.put("qr_pay_mode", payMode);
        }
        if (!ArgsUtils.isEmpty(qrCodeWidth)) {
            paramMapTemp.put("qrcode_width", qrCodeWidth);
        }

        paramMapTemp.put("out_trade_no", orderNo);// 商户订单号,必填
        paramMapTemp.put("subject", orderName);// 订单名称,必填
        Double totalFeeD = totalFee / 100.0d;
        paramMapTemp.put("total_fee", totalFeeD.toString());// 付款金额,必填
        if (!ArgsUtils.isEmpty(desc)) {
            paramMapTemp.put("body", desc);// 商品描述,可填
        }
        return paramMapTemp;
    }

    /**
     * 支付宝,发送请求
     *
     * @param request
     * @param orderNo        订单号,必填
     * @param orderName      订单名称,产品的整体对外称呼(如：鹏博士英语、鹏博士课堂、大乐透等),必填,
     * @param totalFee       金额,分,必填
     * @param desc           描述,一般由商品的名称+唯一键组成(uniqId,如果没有的话,则不要),选填
     * @param timeoutExpress 付款最晚时间,单位分钟,选填
     */
    public PayReturnDto alipayNew(HttpServletRequest request, //
                                  String orderNo, //
                                  String orderName, //
                                  Integer totalFee, //
                                  String desc, //
                                  Integer timeoutExpress,//
                                  TradeTypeEnum tradeTypeEnum
    ) {
        // 1.客户端基础参数初始化
        AlipayClient alipayClient = new DefaultAlipayClient(
                this.alipayConfigNew.aliGateway,
                this.alipayConfigNew.appid,
                this.alipayConfigNew.private_key,
                this.alipayConfigNew.format_json,
                this.alipayConfigNew.input_charset,
                this.alipayConfigNew.public_key,
                this.alipayConfigNew.sign_type);
        // 2.设置公共参数
        Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
        mapParam.put("body", desc);
        mapParam.put("out_trade_no", orderNo);// 订单号
        Double totalFeeD = totalFee / 100.0d;
        mapParam.put("total_amount", totalFeeD.toString());
        mapParam.put("subject", orderName);
        int _timeoutExpress = 16;
        if (!ArgsUtils.isEmptyOrMinus(timeoutExpress)) {
            if (timeoutExpress < 16) {
                timeoutExpress = 16;
            } else {
                _timeoutExpress = timeoutExpress;
            }
        }

        AlipayResponse responseResult = null;
        try {
            // 3.根据不同的支付类型,创建不同
            switch (tradeTypeEnum) {
                case PC_OLD:// 返回form表单
                    mapParam.put("product_code", "FAST_INSTANT_TRADE_PAY");
                    if (!ArgsUtils.isEmptyOrMinus(timeoutExpress)) {
                        mapParam.put("time_expire", ArgsUtils.formatDate(ArgsUtils.getDateMinute(Calendar.getInstance().getTime(), timeoutExpress), "yyyy-MM-dd HH:mm:ss"));
                    }
                    // AlipayTradePagePayResponse
                    AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
                    alipayTradePagePayRequest.setNotifyUrl(String.format(this.alipayConfigNew.notify_url, orderNo));
                    alipayTradePagePayRequest.setReturnUrl(this.alipayConfigNew.return_url);
                    alipayTradePagePayRequest.setBizContent(JsonUtils.toJson(mapParam));
                    responseResult = alipayClient.pageExecute(alipayTradePagePayRequest);
                    break;
                case PC:// 返回二维码
                    // AlipayTradePrecreateResponse
                    mapParam.put("timeout_express", _timeoutExpress + "m");
                    AlipayTradePrecreateRequest alipayTradePrecreateRequest = new AlipayTradePrecreateRequest();
                    alipayTradePrecreateRequest.setNotifyUrl(String.format(this.alipayConfigNew.notify_url, orderNo));
                    alipayTradePrecreateRequest.setReturnUrl(this.alipayConfigNew.return_url);
                    alipayTradePrecreateRequest.setBizContent(JsonUtils.toJson(mapParam));
                    responseResult = alipayClient.execute(alipayTradePrecreateRequest);
                    break;
                case H5:// 返回一个地址链接
                    // AlipayTradeWapPayResponse
                    mapParam.put("quit_url", this.alipayConfigNew.quit_url);//中途退出支付所跳转的页面
                    mapParam.put("product_code", "QUICK_WAP_WAY");
                    AlipayTradeWapPayRequest alipayTradeWapPayRequest = new AlipayTradeWapPayRequest();
                    alipayTradeWapPayRequest.setNotifyUrl(String.format(this.alipayConfigNew.notify_url, orderNo));
                    alipayTradeWapPayRequest.setReturnUrl(this.alipayConfigNew.return_url);
                    alipayTradeWapPayRequest.setBizContent(JsonUtils.toJson(mapParam));
                    responseResult = alipayClient.pageExecute(alipayTradeWapPayRequest);
                    break;
            }
            logger.info("支付宝调用结果:" + JsonUtils.toJson(responseResult));
            // 4.判断结果
            if (responseResult != null && responseResult.isSuccess()) {
                PayReturnDto payReturnDto = new PayReturnDto();
                switch (tradeTypeEnum) {
                    case PC_OLD://AlipayTradePagePayResponse
                        // 获取body,form表单
                        payReturnDto.setFormBody(responseResult.getBody());
                        break;
                    case PC:// AlipayTradePrecreateResponse
                        // 主要是获取二维码,qr_code
                        payReturnDto.setTradeNumber(((AlipayTradePrecreateResponse) responseResult).getOutTradeNo());
                        payReturnDto.setQrCode(((AlipayTradePrecreateResponse) responseResult).getQrCode());
                        break;
                    case H5:// AlipayTradeWapPayResponse
                        // 获取body,form表单
                        payReturnDto.setFormBody(responseResult.getBody());
                        break;
                }
                return payReturnDto;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new ServiceException("调用支付宝接口错误");
        }
        return null;
    }

    /**
     * 微信调用支付
     *
     * @param request
     * @param orderNo    订单号
     * @param orderName  订单名称
     * @param totalFee   金额,分
     * @param timeStart  订单开始时间,yyyyMMddHHmmss
     * @param timeExpire 订单失效时间,yyyyMMddHHmmss
     * @return
     */
    public PayReturnDto wxpay(HttpServletRequest request, String orderNo, String orderName, Integer totalFee,
                              String timeStart, String timeExpire, String openid, TradeTypeEnum tradeType) {
        // 回掉地址
        String notify_url = String.format(PropertiesUtils.getProperty("weChatpay_notify_url"), orderNo);
        // 构建参数
        try {
            /* 注释掉了，现在数据库里面存的就是分 */
            UnifiedorderReqData reqData = null;
            switch (tradeType) {
                case PC:
                    reqData = new UnifiedorderReqData().pc(orderNo, orderName,
                            String.valueOf(totalFee.longValue()), orderNo, notify_url, WebUtils.getIpAddr(request), null,
                            timeStart, timeExpire, wxConfigure);
                    break;
                case H5:
                    reqData = new UnifiedorderReqData().h5(orderNo, orderName,
                            String.valueOf(totalFee.longValue()), orderNo, notify_url, WebUtils.getIpAddr(request), null,
                            timeStart, timeExpire, wxConfigure);
                    break;
                case JSAPI:
                    reqData = new UnifiedorderReqData().jsapi(orderNo, orderName,
                            String.valueOf(totalFee.longValue()), orderNo, notify_url, WebUtils.getIpAddr(request), null,
                            timeStart, timeExpire, openid, wxConfigure);
                    break;
            }
            // 1.请求并返回结果
            String resultXml = null;
            for (int i = 0; i < 5; i++) {
                resultXml = new Unifiedorder(wxConfigure).request(reqData);
                logger.info("微信[统一下单]调用结果:" + resultXml);
                if (!ArgsUtils.isEmpty(resultXml)) {
                    break;
                }
                Thread.sleep(500);
            }
            if (resultXml == null) {
                logger.info("微信[统一下单]调用结果,访问连接超时......");
                return null;
            }
            UnifiedorderResData resData = (UnifiedorderResData) Util
                    .getObjectFromXML(resultXml, UnifiedorderResData.class);
            logger.info("微信[统一下单]调用结果:" + resData.toString());
            // 2.判断是否成功
            if (resData != null && "SUCCESS".equalsIgnoreCase(resData.getReturn_code())
                    && "SUCCESS".equalsIgnoreCase(resData.getResult_code())) {
                PayReturnDto returnDto = new PayReturnDto();
                returnDto.setQrCode(resData.getCode_url());
                returnDto.setTradeNumber(resData.getPrepay_id());
                returnDto.setMwebUrl(resData.getMweb_url());
                return returnDto;
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new ServiceException("调用微信支付失败");
        }
    }

    public Map<String, Object> wxJsapiSign(String prepay_id) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("appId", wxConfigure.getAppid());
        returnMap.put("timeStamp", System.currentTimeMillis());
        returnMap.put("nonceStr", ArgsUtils.getRandomStr(32));
        returnMap.put("package", "prepay_id=" + prepay_id);
        returnMap.put("signType", "MD5");
        String sign = Signature.getSign(returnMap, wxConfigure);
        returnMap.put("paySign", sign);
        return returnMap;
    }

    public static class PayReturnDto implements Serializable {

        private static final long serialVersionUID = -903235179500051278L;
        private String qrCode;// 二维码地址,

        private String tradeNumber;// 交易号

        private String mwebUrl;// h5支付返回值

        private String formBody;// form表单
        // 用formBody,这样写
        // response.setContentType("text/html;charset=" + alipayConfigNew.input_charset);
        // response.getWriter().write(requestUrl2);// 直接将完整的表单html输出到页面
        // response.getWriter().flush();
        // response.getWriter().close();

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getTradeNumber() {
            return tradeNumber;
        }

        public void setTradeNumber(String tradeNumber) {
            this.tradeNumber = tradeNumber;
        }

        public String getMwebUrl() {
            return mwebUrl;
        }

        public void setMwebUrl(String mwebUrl) {
            this.mwebUrl = mwebUrl;
        }

        public String getFormBody() {
            return formBody;
        }

        public void setFormBody(String formBody) {
            this.formBody = formBody;
        }

    }

    public enum TradeTypeEnum {

        JSAPI("JSAPI"),// 适用于微信
        H5("MWEB"),// 跳转地址
        PC("NATIVE"),// 二维码
        PC_OLD("NATIVE"),// 它不适用微信,返回的可以跳转页面
        APP("APP"),
        ;

        private String name;

        private TradeTypeEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

}
