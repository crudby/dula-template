package com.crud.dula.platform.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author crud
 * @date 2023/9/15
 */
@Data
public class WechatLoginDTO {

    @NotBlank
    private String code;

}
