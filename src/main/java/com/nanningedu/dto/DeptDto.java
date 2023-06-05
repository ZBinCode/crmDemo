package com.nanningedu.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/*
* 做部门的添加功能时，需要使用该dto
* 定义校验规则
* */
@Data
public class DeptDto {

    //主键
    private Long id;

    @NotNull
    @Pattern(regexp = ".{3,10}")
    private String name;

    @NotNull
    @Pattern(regexp = ".{10,20}")
    private String loc;

}
