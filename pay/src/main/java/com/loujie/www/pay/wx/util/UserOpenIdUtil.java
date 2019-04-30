package com.loujie.www.pay.wx.util;


import com.loujie.www.util.common.ArgsUtils;

import java.util.Map;

/**
 * 获取用户的openid
 *
 * @name loujie
 * @date 2019/4/18
 */
public class UserOpenIdUtil {

    private static final String openIdApiUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";


    /**
     * 获取用户的openid
     *
     * @param appid  公众号Id
     * @param secret 公众号appsecret
     * @param code   页面的code
     * @return
     */
    public static String getUserOpenId(String appid, String secret, String code) {
        // 本接口的意图,请参见地址：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
        // jsapi是在微信浏览器中唤起支付,需要用户的openid,openid:每一个微信用户针对固定的公众号openid都是固定的
        // 获取code,需要页面自己先跳转通过微信的一个请求,需要的参数:appid,redirect_uri=原来的地址(他需要在公众平台配置),response_type=code,scope=snsapi_base,state=STATE#wechat_redirect
        // 例如:https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
        // 这样请求过后,新跳转的页面地址中就有code码了,就可以请求当前的接口了,可以获取用户的openid

        // 1.整理出请求地址
        String url = openIdApiUrl.replace("{appid}", appid).replace("{secret}", secret).replace("{code}", code);
        // 2.调用接口,获取信息
        Map<String, Object> resultMap = null;// TODO ArgsUtils.httpsGetToMap(url, null, null);
        // {
        //     "access_token":"ACCESS_TOKEN",
        //     "expires_in":7200,
        //     "refresh_token":"REFRESH_TOKEN",
        //     "openid":"OPENID",
        //     "scope":"SCOPE"
        // }
        String openid = null;
        if (resultMap != null && resultMap.size() > 0) {
            openid = ArgsUtils.parseString(resultMap.get("openid"));
        }
        if (!ArgsUtils.isEmpty(openid)) {
            return openid;
        }
        return null;
    }

}
