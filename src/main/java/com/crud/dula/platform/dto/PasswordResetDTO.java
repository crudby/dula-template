package com.crud.dula.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author crud
 * @date 2023/9/15
 */
@ApiModel(description = "密码重置DTO")
@Data
public class PasswordResetDTO {

    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank
    private String oldPassword;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank
    private String newPassword;
}
