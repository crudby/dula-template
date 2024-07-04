package com.crud.dula.platform.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author crud
 * @date 2023/10/11
 */
@ApiModel(description = "用户信息VO")
@Data
public class SysUserVO {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;
    private String avatarUrl;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String username;

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
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    /**
     * 是否注册
     */
    @ApiModelProperty("是否注册")
    private Boolean registered;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

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
