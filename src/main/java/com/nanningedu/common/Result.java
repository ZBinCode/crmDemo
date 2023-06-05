package com.nanningedu.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 存储接口返回的结果
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    //状态码  200请求成功  -1请求失败
    private Integer code = 200;

    //请求失败之后，显示错误信息
    private String msg;

    //保存返回的数据
    private Object data;

    //获取到分页的总数
    private Long total = 0L;

    //前端传递的数据就是错误的
    public static final Result DATA_FORMAT_ERROR = new Result(-1,"前端参数请求错误");

    //报错时的显示方式
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
