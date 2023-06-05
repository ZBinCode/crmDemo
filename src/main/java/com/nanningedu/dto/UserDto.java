package com.nanningedu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;

    @NotNull(message = "姓名不能为空！")
    @Pattern(regexp = "[\\u4e00-\\u9fa5]{2,10}",message = "长度为2到10位的简体中文")
    private String username;

    // 确保前端到后端的时间格式一致
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull(message = "性别不能为空！")
    @Pattern(regexp = "[10]",message = "性别格式错误！")
    private String sex;

    @NotNull(message = "联系电话不能为空！")
    @Pattern(regexp = "[1][345789]\\d{9}",message = "手机号码格式错误！")
    private String tel;

    @Min(value = 0L,message = "薪资格式错误！")
    private Double sal;

    @NotNull(message = "职业不能为空！")
    @Pattern(regexp = "[123]",message = "职业格式错误！")
    private String profession;

    @NotNull(message = "地址不能为空！")
    @Pattern(regexp = ".{10,50}",message = "地址格式错误！")
    private String address;
    private String remark;
    private String name;
    private String loc;
    private Long deptId;
}
