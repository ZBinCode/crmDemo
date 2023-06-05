package com.nanningedu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/*
* 做部门的添加功能时，需要使用该dto
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeptDto {

    private Long id;

    @NotNull
    @Pattern(regexp = ".{3,10}")
    private String name;

    @NotNull
    @Pattern(regexp = ".{10,20}")
    private String loc;

}
