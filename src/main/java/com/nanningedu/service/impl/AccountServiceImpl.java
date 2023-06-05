package com.nanningedu.service.impl;

import com.nanningedu.common.Constants;
import com.nanningedu.common.Result;
import com.nanningedu.domain.Account;
import com.nanningedu.dto.AccountDto;
import com.nanningedu.mapper.AccountMapper;
import com.nanningedu.service.AccountService;
import com.nanningedu.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/*
 * 实现类
 * 该实现类去实现接口类
 * */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    //重写接口类里面的方法
    @Override
    public Result findLogin(AccountDto accountDto, HttpSession session) {
        //从后端缓存中，获取验证码
        //当使用admin进行登录操作时，会将验证码一起保存到缓存中，
        // 只要不是admin重新进行登录，那么admin对应的这个验证码就不会在改变
        String code = session.getAttribute(Constants.CODE_SESSION_KEY)+"";
        //输入的验证码与缓存中的验证码进行比较
        if(!(accountDto.getCaptcha().equals(code))){
            //显示错误
            return new Result(-1,"验证码错误");
        }
        //密码加密：为了账号安全
        String encry = MD5Util.finalMD5(accountDto.getPwd());
        //把加密之后的密码，保存到数据库去
        accountDto.setPwd(encry);
        //登录
        Account account = accountMapper.selectByLogin(accountDto);
        if(account == null){
            return new Result(-1,"用户名或者密码登录失败");
        }
        //登录成功之后，要将登录过程中输入的数据，保存到缓存中
        //通过account.getUsername()获取到输入的用户名，然后保存到USER_SESSION_KEY缓存中
        session.setAttribute(Constants.USER_SESSION_KEY,account.getUsername());
        //将登录人的角色，保存到缓存中
        session.setAttribute(Constants.ROLE_SESSION_KEY,account.getRole());
        //将登录人的id，保存到缓存中
        session.setAttribute(Constants.USER_SESSION_ID,account.getId());
        Result result = new Result();
        result.setData(account);
        return result;
    }

    @Override
    public Result modifyUploadHeadImg(String imgUrl, HttpSession session) {
        //获取到登录人的id
        String userId = session.getAttribute(Constants.USER_SESSION_ID) + "";
        Long id = Long.parseLong(userId);
        int i = accountMapper.updateAccountImgUrl(imgUrl,id);
        if(i == 0){
            return new Result(-1,"文件上传失败");
        }
        return new Result();
    }

    //状态的禁用与启用
    @Override
    public Result modifyAccountStatus(Integer status,Long id,HttpSession session) {
        //校验数据
        if(!(status == 0 || status == 1) || id <= 0){
            return Result.DATA_FORMAT_ERROR;
        }
        //判断此操作是否是超级管理员在进行操作
        String role = session.getAttribute(Constants.ROLE_SESSION_KEY)+"";
        if(!(role.equals("1"))){
            return new Result(-1,"你不是超级管理员，没有此操作权限");
        }
        int i = accountMapper.updateAccountStatus(status,id);
        if(i == 0){
            return new Result(-1,"修改状态失败");
        }
        return new Result();
    }

}
