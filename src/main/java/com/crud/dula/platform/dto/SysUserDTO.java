package com.crud.dula.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author crud
 * @date 2023/9/15
 */
@ApiModel(description = "用户信息DTO")
@Data
public class SysUserDTO {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * openID
     */
    @ApiModelProperty("openID")
    private String openId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名", required = true)
    @NotBlank
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", required = true)
    @NotBlank
    private String nickname;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 最后登录IP
     */
    @ApiModelProperty("最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginTime;

    /**
     * 帐号状态（1启用 0禁用）
     */
    @ApiModelProperty("帐号状态（1启用 0禁用）")
    private Boolean enabled;

}
