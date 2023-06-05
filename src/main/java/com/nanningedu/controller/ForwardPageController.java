package com.nanningedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
* 用于跳转到控制层
* */
@Controller
@RequestMapping("/forward")
public class ForwardPageController {

    //跳转到登录页面
    @RequestMapping("/toLogin.do")
    public String toLogin(){
        return "login";
    }

    //登录成功之后，去到主页，点击主页的导航栏，也需要跳转到对应的页面
    @RequestMapping("/toPage.do")
    public String toPage(String pageUrl){
        return pageUrl;
    }

}
