package com.loujie.www.pay.ali.util;

import com.loujie.www.pay.ali.config.AlipayConfigOld;
import com.loujie.www.pay.ali.sign.RSA;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipaySubmit {

    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

    /**
     * 生成签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, String> sPara, AlipayConfigOld alipayConfigOld) {
        String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if (alipayConfigOld.sign_type.equalsIgnoreCase("RSA")) {
            mysign = RSA.sign(prestr, alipayConfigOld.private_key, alipayConfigOld.input_charset);
        }
        return mysign;
    }

    /**
     * 生成要请求给支付宝的参数数组
     *
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, AlipayConfigOld alipayConfigOld) {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = buildRequestMysign(sPara, alipayConfigOld);

        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", alipayConfigOld.sign_type);

        return sPara;
    }

    /**
     * 建立一个http请求连接
     *
     * @param sParaTemp 参数
     * @return
     */
    public static String buildRequestUrl(Map<String, String> sParaTemp, boolean isEncoder, AlipayConfigOld alipayConfigOld) {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, alipayConfigOld);
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(ALIPAY_GATEWAY_NEW);
        if (isEncoder) {
            try {
                sBuilder.append(AlipayCore.createLinkStringEncoder(sPara));
            } catch (UnsupportedEncodingException e) {
            }
        } else {
            sBuilder.append(AlipayCore.createLinkString(sPara));
        }
        return sBuilder.toString();
    }

    /**
     * 建立请求，以表单HTML形式构造（默认）
     *
     * @param sParaTemp     请求参数数组
     * @param strMethod     提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     * @throws UnsupportedEncodingException
     */
    public static String buildRequestGet(Map<String, String> sParaTemp, String strMethod, String strButtonName, AlipayConfigOld alipayConfigOld) throws UnsupportedEncodingException {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, alipayConfigOld);
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW + AlipayCore.createLinkStringEncoder(sPara) + "\" method=\"" + strMethod + "\">");
        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.getElementById('alipaysubmit').submit();</script>");
        return sbHtml.toString();
    }

    /**
     * 建立请求，以表单HTML形式构造（默认）
     *
     * @param sParaTemp     请求参数数组
     * @param strMethod     提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName, AlipayConfigOld alipayConfigOld) {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, alipayConfigOld);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW + "\" method=\"" + strMethod + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }

    /**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     *
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
    @SuppressWarnings("unchecked")
    public static String query_timestamp(AlipayConfigOld alipayConfigOld) throws MalformedURLException, DocumentException, IOException {

        // 构造访问query_timestamp接口的URL串
        String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + alipayConfigOld.partner + "&_input_charset" + alipayConfigOld.input_charset;
        StringBuffer result = new StringBuffer();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());

        List<Node> nodeList = doc.selectNodes("//alipay/*");

        for (Node node : nodeList) {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }

        return result.toString();
    }
}
