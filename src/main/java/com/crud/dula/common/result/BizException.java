package com.crud.dula.common.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author crud
 * @date 2023/9/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException{

    /**
     * 异常编码
     */
    private Long code;

    public BizException(Long code) {
        this.code = code;
    }

    public BizException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BizException(Long code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCode.SYSTEM_ERROR.getCode();
    }
}
