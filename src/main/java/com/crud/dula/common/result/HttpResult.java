package com.crud.dula.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author crud
 * @date 2023/7/7
 */
@Data
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success;

    private Long code;

    private String message;

    private T data;

    private HttpResult(Boolean success, Long code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> HttpResult<T> success(T t) {
        return new HttpResult<>(Boolean.TRUE, ResultCode.SUCCESS.code, ResultCode.SUCCESS.message, t);
    }

    public static <T> HttpResult<T> success() {
        return success(null);
    }

    public static <T> HttpResult<T> fail(Long code, String message, T data) {
        return new HttpResult<>(Boolean.FALSE, code, message, data);
    }

    public static <T> HttpResult<T> fail(String message, T data) {
        return fail(ResultCode.SYSTEM_ERROR.code, message, data);
    }

    public static <T> HttpResult<T> fail(Long code, String message) {
        return fail(code, message, null);
    }

    public static <T> HttpResult<T> fail(String message) {
        return fail(message, null);
    }

    public static HttpResult<String> fail(BizException bizException) {
        return fail(bizException.getCode(), bizException.getMessage(), null);
    }

    public static HttpResult<String> fail(ResultCode resultCode) {
        return fail(resultCode.getCode(), resultCode.getMessage(), null);
    }
}
