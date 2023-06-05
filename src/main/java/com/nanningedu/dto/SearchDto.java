package com.nanningedu.dto;

import lombok.Data;

/*
* 搜索功能的dto
* */
@Data
public class SearchDto extends BaseDto{

    private String username;
    private String tel;
    private String deptName;
    private String sex;

}
