package com.loujie.www.dwz.controller.login;

import org.patchca.color.ColorFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * @name loujie
 * @date 2019/3/8
 */
@Controller
@RequestMapping
public class CaptchaController {

    private static final ConfigurableCaptchaService ccs = new ConfigurableCaptchaService();
    private static Random random = new Random();

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
        ccs.setWidth(75);
        ccs.setHeight(30);
    }

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response, Long datelong) throws IOException {
        this.setResponseHeaders(response);
        // 更改图片
        this.setFilterFactory();
        // 生成验证码
        String token = EncoderHelper.getChallangeAndWriteImage(ccs, "png", response.getOutputStream());
        // 保存到session中
        request.getSession().setAttribute("captchaToken", token);
    }

    protected void setFilterFactory() {
        /*switch (random.nextInt(5)) {
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
        }*/
    }

    protected void setResponseHeaders(HttpServletResponse response) {
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

}
