package com.crud.dula.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author crud
 * @date 2023/9/15
 */
@ApiModel(description = "密码更新DTO")
@Data
public class PasswordInitDTO {

    /**
     * 令牌
     */
    @ApiModelProperty(value = "token", required = true)
    @NotBlank
    private String token;

    /**
     * 密码
     */
    @ApiModelProperty(value = "password", required = true)
    @NotBlank
    private String password;
}
