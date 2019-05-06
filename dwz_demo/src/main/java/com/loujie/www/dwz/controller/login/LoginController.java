package com.loujie.www.dwz.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @name loujie
 * @date 2019/3/7
 */
@Controller
@RequestMapping
public class LoginController {

    private static final String LOGIN = "/login";

    /**
     * 1.跳转登录页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        // 判断是否登录了,如果登录了,跳转成成功页面
        return LOGIN;
    }

    /**
     * 2.登录
     *
     * @param username   登录用户名
     * @param password   登录密码
     * @param captchaKey 图片验证码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "captcha_key") String captchaKey
    ) {

        return LOGIN;
    }


}
