package com.loujie.www.util.captcha;

import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.*;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @name loujie
 * @date 2019/3/21
 */
public class PictureVerificationCodeUtil {

    private static final ConfigurableCaptchaService ccs = new ConfigurableCaptchaService();
    private static Random random = new Random();
    private static int DEFAULT_WIDTH = 75;
    private static int DEFAULT_HEIGHT = 30;

    static {
        ccs.setColorFactory(new ColorFactory() {
            @Override
            public Color getColor(int x) {
                int[] c = new int[3];
                int i = random.nextInt(c.length);
                for (int fi = 0; fi < c.length; fi++) {
                    if (fi == i) {
                        c[fi] = random.nextInt(71);
                    } else {
                        c[fi] = random.nextInt(256);
                    }
                }
                return new Color(c[0], c[1], c[2]);
            }
        });
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");
        wf.setMaxLength(4);
        wf.setMinLength(4);
        ccs.setWordFactory(wf);
        ccs.setWidth(DEFAULT_WIDTH);
        ccs.setHeight(DEFAULT_HEIGHT);
    }

    /**
     * 改变图片的背景、字体、混淆程度
     */
    private static void setFilterFactory() {
        switch (random.nextInt(5)) {
            case 0:
                ccs.setFilterFactory(new CurvesRippleFilterFactory(ccs.getColorFactory()));
                break;
            case 1:
                ccs.setFilterFactory(new MarbleRippleFilterFactory());
                break;
            case 2:
                ccs.setFilterFactory(new DoubleRippleFilterFactory());
                break;
            case 3:
                ccs.setFilterFactory(new WobbleRippleFilterFactory());
                break;
            case 4:
                ccs.setFilterFactory(new DiffuseRippleFilterFactory());
                break;
        }
    }

    /**
     * 设置Response的头部信息
     *
     * @param response
     */
    public static void setResponseHeaders(HttpServletResponse response) {
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
        response.setContentType("image/png");
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
    }

    /**
     * 生成图片验证码，将验证码直接输出到web页面，返回验证码值，让存入session
     *
     * @param response
     * @return
     */
    public static String getCaptchaForWeb(HttpServletResponse response) {
        String result = null;
        try {
            result = EncoderHelper.getChallangeAndWriteImage(ccs, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成图片验证码,
     *
     * @return Map, 里面包含了验证码值=pic_val及图片流=pic_io
     */
    public synchronized static Map<String, Object> getCaptchaForApi(int width, int height) {
        ccs.setWidth(width);
        ccs.setHeight(height);
        Map<String, Object> returnMap = new HashMap<>();
        Captcha captcha = ccs.getCaptcha();
        returnMap.put("pic_val", captcha.getChallenge());//验证码值
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Img = encoder.encode(outputStream.toByteArray());
        returnMap.put("pic_bin", base64Img);
        return returnMap;
    }

    public synchronized static Map<String, Object> getCaptchaForApi() {
        return getCaptchaForApi(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
