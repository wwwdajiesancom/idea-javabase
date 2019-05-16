package com.loujie.www.test;

import com.loujie.www.okhttp.OkHttpUtils;
import com.loujie.www.test.base.IBaseTest;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * h5端的限制
 *
 * @author loujie
 */
public class JiaoyubaoH5Test implements IBaseTest {


    private String scene = "FORGET_PWD";

    // 1.图片验证码
    @Test
    public void captchaTest() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            int fi = i;
            executorService.execute(() -> {
                Map<String, Object> paraMap = new HashMap<String, Object>();
                paraMap.put("scene", scene);
                paraMap.put("r", System.currentTimeMillis());
                String resultString = OkHttpUtils.POST.run(this.getUrl("/captcha"), paraMap);
                System.out.println(fi + ":" + System.currentTimeMillis() + ":" + resultString);
            });
        }
        this.awaits();
        System.out.println("---end");
    }

    String checkval = "0c2e81a899e5462484aac1165305cf3d";
    String pic_code = "xhdc";
    String topic = "15652798651";//"15838022425";


    // 2.验证图片验证码
    @Test
    public void validCaptchaTest() {
        executorService.execute(() -> {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("scene", scene);
            paraMap.put("checkval", checkval);// captchaTest的返回值
            paraMap.put("pic_code", pic_code);// 图片验证码
            paraMap.put("topic", topic);// 注册时的手机号码
            String result = OkHttpUtils.POST.run(this.getUrl("/valid_captcha"), paraMap);
            System.out.println(result);
        });
        this.awaits();
        System.out.println("---end");
    }

    String checkval2 = "e8aa3cac73e24743a827639a93fc14a9";
    String mobile = topic;

    String mesCode = "189741";

    // 3.发送短信
    @Test
    public void sendMsgTest() {
        executorService.execute(() -> {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("sence", scene);
            paraMap.put("checkval", checkval2);// validCaptchaTest的返回值
            paraMap.put("mobile", mobile);// 手机号码,与validCaptchaTest的topic一样
            String result = OkHttpUtils.POST.run(this.getUrl("/send_msg"), paraMap);
            System.out.println(result);
        });
        this.awaits();
        System.out.println("---end");
    }


    String checkval3 = checkval2;

    // 4.验证是否注册了
    @Test
    public void checkRegisterTest() {
        executorService.execute(() -> {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("checkval", checkval3);// validCaptchaTest的返回值
            paraMap.put("mobile", mobile);// 手机号码,与validCaptchaTest的topic一样
            String result = OkHttpUtils.POST.run(this.getUrl("/check_register"), paraMap);
            System.out.println(result);
        });
        this.awaits();
        System.out.println("---end");
    }

    // 5.登录
    @Test
    public void loginTest() {
        executorService.execute(() -> {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            String mac = "143DF290C58F";
            String sn = "DBD3410H1800100112";
            String type = "1";
            paraMap.put("topic", mac + "," + sn + "," + type);

            paraMap.put("userName", "15652798651");
            paraMap.put("password", "123456789a");

            paraMap.put("userName", "18234004832");
            paraMap.put("password", "11111111g");

            paraMap.put("userName", "18513108536");
            paraMap.put("password", "q11111111");

            paraMap.put("userName", "15600119310");
            paraMap.put("password", "a123456");

            paraMap.put("userName", "13321138521");
            paraMap.put("password", "a1234568");

            paraMap.put("userName", "15838022425");
            paraMap.put("password", "a123456a");


            String result = OkHttpUtils.POST.run(this.getUrl("/login"), paraMap);
            System.out.println(result);
        });
        this.awaits();
        System.out.println("---end");
    }

    String password = "a1234567890";

    // 6.注册
    @Test
    public void registerTest() {
        executorService.execute(() -> {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("mobile", mobile);// 手机号码,与validCaptchaTest的topic一样
            paraMap.put("mesCode", mesCode);// 手机验证码
            paraMap.put("password", password);// 登录需要的密码
            String result = OkHttpUtils.POST.run(this.getUrl("/register"), paraMap);
            System.out.println(result);
        });
        this.awaits();
        System.out.println("---end");
    }

    String newpassword = "123456789a";

    // 7.忘记密码
    @Test
    public void forgetPwdTest() {
        executorService.execute(() -> {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("mobile", mobile);// 手机号码,与validCaptchaTest的topic一样
            paraMap.put("mesCode", mesCode);// 手机验证码
            paraMap.put("password", newpassword);// 登录需要的密码
            String result = OkHttpUtils.POST.run(this.getUrl("/forget_pwd"), paraMap);
            System.out.println(result);
        });
        this.awaits();
        System.out.println("---end");
    }


    @Override
    public String getUrl(String url_suffix) {
        return getUrlPrefix() + "/openapi/boxh5" + url_suffix;
    }

    @Test
    public void url() {
        System.out.println(URLEncoder.encode(""));
    }

    private static final List<String> hz_list = new ArrayList<>();

    static {
        hz_list.add(".pdf");

        // 图片
        hz_list.add(".jpg");
        hz_list.add(".jpeg");
        hz_list.add(".png");
        hz_list.add(".bmp");
        hz_list.add(".gif");
    }

    @Test
    public void url2() {
        String credentials = null;
        for (String credential : "aa.pdsf,bb.xpdf,x.psdf,index.pdff,2index.jpsg".split(",")) {
            String t_hz = credential.substring(credential.lastIndexOf("."));
            if (hz_list.contains(t_hz.toLowerCase())) {
                if (credentials == null || credentials.isEmpty()) {
                    credentials = credential;
                } else {
                    credentials += "," + credential;
                }
            }
        }
        System.out.println(credentials);
    }
}

