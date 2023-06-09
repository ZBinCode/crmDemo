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

    //分页查询
    Result findAccountByPage(Integer pageNum, Integer pageSize);

    //添加用户
    Result saveAccount(String username);

    //删除用户
    Result removeOneAccount(Long id);

    //重置密码
    Result modifAccountPwd(Long id,String pwd);
}
