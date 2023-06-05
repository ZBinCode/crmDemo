package com.nanningedu.utils;

import cn.hutool.crypto.SecureUtil;

/*
* 密码加密的类
* */
public class MD5Util {

    private static String init(String txt){
        if(txt == null){
            return null;
        }
        String encry = SecureUtil.md5(txt);
        return encry;
    }

    //为了确保加密的结果更加的安全，可以使用加盐
    public static String finalMD5(String txt){
        return init(init(init(init(txt)+"bigbird")+"superbird")+"bigbird");
    }

    public static void main(String[] args) {
        System.out.println(finalMD5("123456"));
    }

}
