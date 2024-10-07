package org.cyk.ktearth.infra.model

/**
 * 用来表明 api 的不同状态
 */
enum class ApiStatus(val code: Int, val msg: String) {
    //请求成功
    SUCCESS(0, "ok"),
 
    //请求失败
    FAIL(1, "request failed"),
 
    //非法请求
    INVALID_REQUEST(2, "invalid request"),
 
    //非法参数
    INVALID_PARAM(3, "invalid param"),
 
    //未绑定
    NOT_BINDED(4, "not binded"),
 
    //服务器错误
    SERVER_ERROR(5, "server error"),
 
    //没有注册
    NOT_REGISTERED(6, "not registered"),
 
    //token 过期
    JWT_EXPIRE(7, "jwt expire"),

    //请求过快
    REQUEST_TOO_FAST(8, "request too fast"),

    //访问频率被限制
    FREQUENCY_LIMITED(9, "frequency limited"),

    //没有权限
    NO_PERMISSION(10, "no permission"),

    //用户未登陆
    NOT_LOGIN(40001, "require login"),
 
    //用户被封
    USER_LOCKED(40002, "user is locked"),

    //用户头像非法
    INVALID_AVATAR(40003, "user avatar is invalid"),

    //用户名已存在
    USERNAME_OR_PASSWORD_ERROR(40004, "username or password error"),

    //用户名存在
    USERNAME_EXISTS(40005, "username exists"),

    //访问量被限制
    VIEW_LIMITED(40101, "view limited"),
 
    //账单异常
    ACCOUNT_ABNORMAL(40201, "account abnormal"),

    ;
}