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

}
