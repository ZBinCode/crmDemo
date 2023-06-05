package com.nanningedu.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/*
* 要实现登录功能，需要使用的是account这张表，所以就根据account表来创建一个Account类
* 一个类由三部分组成：类、属性、方法
* */
//@Getter  //该注解是用于替换所有的getter方法的
//@Setter  //该注解是用于替换所有的setter方法的
//@NoArgsConstructor  //该注解是用于替换无参构造方法的
@Data    //@Getter + @Setter + @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor //该注解是用于替换带参构造方法的
@ToString
public class Account {

    //类里面的属性，要与数据库表里面的字段一一对应
    //属性（属性要与数据库account表里面的字段保持一致）
    //私有 （这些属性只属于Account这个类）
    private Long id;
    private String username;
    private String pwd;
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

    //因为每一个类，都会默认有一个无参构造器，即使不定义无参构造，也可以使用无参构造
    //如果不定义无参构造，但是定义带参构造，这时还能不能使用默认无参构造
    //这时就不能使用默认的那个无参构造了，原因是带参构造会覆盖掉默认的那个无参构造
    //如果还是想使用无参构造，直接创建一个无参构造就可以了

}
