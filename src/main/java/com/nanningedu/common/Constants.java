package com.nanningedu.common;

/*
* 将数据保存在后端缓存中
* */
public class Constants {

    //标记登录人
    public static final String USER_SESSION_KEY = "USER_SESSION_KEY";

    //标记登录人的id
    public static final String USER_SESSION_ID = "USER_SESSION_ID";

    //标记登录人的角色
    public static final String ROLE_SESSION_KEY = "ROLE_SESSION_KEY";

    //验证码
    /*
    * 1、生成验证码
    * 2、要把验证码保存到CODE_SESSION_KEY这个缓存对象中
    * 3、从CODE_SESSION_KEY获取验证码
    * */
    public static final String CODE_SESSION_KEY = "CODE_SESSION_KEY";

    //头像
    public static final String DEFAULT_HEAD_IMG = "default.png";

}
