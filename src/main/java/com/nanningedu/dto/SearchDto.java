package com.nanningedu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
