package com.crud.dula.platform.web;

import com.crud.dula.common.result.HttpResult;
import com.crud.dula.platform.dto.UserLoginDTO;
import com.crud.dula.platform.dto.WechatLoginDTO;
import com.crud.dula.security.support.LoginSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 系统登录API
 *
 * @author crud
 * @date 2023/9/15
 */
@Api(value = "/sys", tags = {"系统登录API"})
@AllArgsConstructor
@RestController
@RequestMapping("/sys")
public class SysLoginController {

    private final LoginSupport loginSupport;

    /**
     * 用户登录
     *
     * @param userLoginDTO userLoginDTO
     * @return token
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserLoginDTO", name = "userLoginDTO", value = "userLoginDTO", required = true)
    })
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/web/login")
    public HttpResult<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String token = loginSupport.webLogin(userLoginDTO);
        return HttpResult.success(token);
    }

    /**
     * 微信登录
     *
     * @param wechatLoginDTO wechatLoginDTO
     * @return token
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "WechatLoginDTO", name = "wechatLoginDTO", value = "wechatLoginDTO", required = true)
    })
    @ApiOperation(value = "微信登录", notes = "微信登录", httpMethod = "POST")
    @PostMapping("/wechat/login")
    public HttpResult<String> login(@Valid @RequestBody WechatLoginDTO wechatLoginDTO) {
        String token = loginSupport.wechatLogin(wechatLoginDTO);
        return HttpResult.success(token);
    }

}
