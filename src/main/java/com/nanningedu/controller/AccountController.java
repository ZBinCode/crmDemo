package com.nanningedu.controller;

import com.nanningedu.common.Result;
import com.nanningedu.dto.AccountDto;
import com.nanningedu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/*
* 控制层:接收前端传递的数据
* */
@RestController  //@Controller + @ResponseBody
@RequestMapping("/account")   //这是一个路径，通过这个路径可以访问到当前的这个控制层的类
public class AccountController {

    @Autowired
    private AccountService accountService;

    //登录
    @RequestMapping("/getLogin.do")
    public Result getLogin(@Valid AccountDto accountDto, BindingResult br, HttpSession session){
        if(br.hasErrors()){
            return Result.DATA_FORMAT_ERROR;
        }
        return accountService.findLogin(accountDto,session);
    }

}
