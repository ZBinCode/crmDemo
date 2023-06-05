package com.nanningedu.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import com.nanningedu.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
* 生成验证码，并保存到缓存中
* */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @RequestMapping("/getCaptcha.do")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(150,45,4);
        //生成验证码
        String code = gifCaptcha.getCode();
        //讲获取到的验证码，储存到缓存中去
        session.setAttribute(Constants.CODE_SESSION_KEY,code);
        //将验证码通过流的形式写到页面去
        gifCaptcha.write(response.getOutputStream());
    }

}
