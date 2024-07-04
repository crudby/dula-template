package com.crud.dula.common.result;

import lombok.Getter;

/**
 * @author crud
 * @date 2023/9/14
 */
@Getter
public enum ResultCode {


    SUCCESS(0L, "请求成功"),

    BUSINESS_FAILED(100002L, "无法处理"),

    DATA_NOT_EXIST(100003L, "数据不存在"),

    DATA_EXIST(100004L, "数据已存在"),


    USER_NOT_EXIST(200001L, "用户/密码输入错误"),

    USER_CREDENTIALS_ERROR(200002L, "用户/密码输入错误"),

    USER_ACCOUNT_LOCKED(200003L, "账号已锁定"),

    USER_ACCOUNT_DISABLE(200004L, "账号不可用"),

    USER_ACCOUNT_EXPIRED(200005L, "账号已过期"),

    USER_CREDENTIALS_EXPIRED(200006L, "密码已过期"),


    NOT_LOGIN(400401L, "用户未登录"),

    LOGIN_TIMEOUT(400401L, "会话超时，请重新登录"),

    OFFSITE_LOGIN(400402L, "异地登录，会话终止"),

    AUTHORIZATION_DENIED(400403L, "无权限访问"),


    SYSTEM_ERROR(500500L, "系统错误"),

    ;

    public final Long code;

    public final String message;

    ResultCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}
