package com.nanningedu.service;

import com.nanningedu.common.Result;
import com.nanningedu.dto.AccountDto;

import javax.servlet.http.HttpSession;

/*
* 接口类
* */
public interface AccountService {

    //登录
    Result findLogin(AccountDto accountDto, HttpSession session);

    //上传文件
    Result modifyUploadHeadImg(String imgUrl,HttpSession session);

    //状态的禁用和启用
    Result modifyAccountStatus(Integer status,Long id,HttpSession session);

}
