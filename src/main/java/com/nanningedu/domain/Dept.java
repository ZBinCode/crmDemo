package com.nanningedu.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
* 部门类
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dept {

    private Long id;
    private String name;
    private String loc;

}
