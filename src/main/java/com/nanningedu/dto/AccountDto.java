package com.nanningedu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/*
* 不需要使用dto的情况
*       当页面中不需要做输入时
* 需要使用dto的情况
 *       当页面中需要输入时
* dto的作用就是，后端控制所接收的前端传递过来的数据是正确的
* */
@Data
public class AccountDto {

    @NotNull  //输入的内容不能为空
    @Pattern(regexp = "\\w{2,10}")  //不能乱输入
    private String username;

    @NotNull
    @Pattern(regexp = "\\d{6}")
    private String pwd;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z]{4}")
    private String captcha;

    //此属性与数据库表字段对应不上
    private String imgUrl;
    //此属性与数据库表字段对应不上
    //@JsonFormat:确保后端到前端的时间格式保持一致
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;  //2023.5.18  2023/5/18   2023-5-18
    //此属性与数据库表字段对应不上
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    //角色（0是普通管理员  1是超级管理员）
    private Integer role;
    //状态（0是账号禁用  1是账号可用）
    private Integer status;

}
