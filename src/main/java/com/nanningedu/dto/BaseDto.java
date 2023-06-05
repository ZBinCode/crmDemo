package com.nanningedu.dto;

import lombok.Data;

/*
* 分页dto
* */
@Data
public class BaseDto {

    private Integer pageNum = 1;
    private Integer pageSize = 10;

}
