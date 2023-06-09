package com.nanningedu.mapper;

import com.nanningedu.domain.Account;
import com.nanningedu.dto.AccountDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/*
* 接口类
* 接口类中只能定义接口
* */
public interface AccountMapper {

    //登录
    //该接口是以xml映射的方式来实现的
    Account selectByLogin(AccountDto accountDto);

    //将输入的用户名去和数据库表里面的数据进行匹配，如果能匹配得上就登陆成功，如果匹配不上，就登陆失败
    //该接口以注解的方式来实现
    /*
    * 当参数是某一个具体的参数时，那么需要使用匿名参数来传参
    * 匿名参数的两种表示方式
    *       arg  从0开始
    *       param  从1开始
    * */
    @Select("select count(*) from account where username = #{param1}")
    int selectUserNameIsExist(String username);

    //文件上传
    @Update("update account set img_url = #{param1} where id = #{param2}")
    int updateAccountImgUrl(String imgUrl,Long id);

    //修改登录状态
    @Update("update account set status = #{arg0} where id = #{arg1}")
    int updateAccountStatus(Integer status,Long id);

    //用户列表查询
    List<Account> selectAccountByPage();

    //添加用户账号
    int insertAccount(String username);

    //删除用户
    @Delete("delete FROM account WHERE id=#{arg0}")
    int deleteAccount(Long id);

    //修改用户密码
    @Update("update account set pwd=#{param2},update_time=now() where id = #{param1}")
    int updateAccountPwd(Long id,String pwd);

}
