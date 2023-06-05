package com.nanningedu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 分页dto
* */
@Data
public class BaseDto {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
