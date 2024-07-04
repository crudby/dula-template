package com.crud.dula.security.support;

import com.crud.dula.common.jwt.JwtUtil;
import com.crud.dula.common.result.BizException;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.platform.dto.UserLoginDTO;
import com.crud.dula.platform.dto.WechatLoginDTO;
import com.crud.dula.platform.entity.SysUser;
import com.crud.dula.platform.entity.table.SysUserTable;
import com.crud.dula.platform.service.SysUserService;
import com.crud.dula.security.LoginUser;
import com.crud.dula.security.SecurityConstants;
import com.crud.dula.security.session.LoginSession;
import com.crud.dula.security.third.WechatAuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author crud
 * @date 2023/9/15
 */
@Component
public class LoginSupport {

    @Resource(name = "securityAuthenticationManager")
    private AuthenticationManager authenticationManager;

    @Resource
    private LoginSession loginSession;

    @Resource
    private SysUserService sysUserService;

    @Value("${dula.single-active-session:false}")
    private Boolean singleActiveSession;

    /**
     * 密码登录
     * @param userLoginDTO userLoginDTO
     * @return token
     */
    public String webLogin(UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        return this.authenticate(token);
    }

    /**
     * 微信登录
     * @param wechatLoginDTO wechatLoginDTO
     * @return token
     */
    public String wechatLogin(WechatLoginDTO wechatLoginDTO) {
        WechatAuthenticationToken token = new WechatAuthenticationToken(wechatLoginDTO.getCode());
        return this.authenticate(token);
    }


    private String authenticate(Authentication authentication){
        Authentication authenticate = authenticationManager.authenticate(authentication);
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        SysUser sysUser = loginUser.getSysUser();
        if(null == sysUser){
            throw new BizException(ResultCode.NOT_LOGIN);
        }
        // 生成token
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.USER_ID, sysUser.getId());
        if (singleActiveSession) {
            String signStamp = SecurityConstants.SINGLE_ACTIVE_SESSION + System.currentTimeMillis();
            loginUser.setSignStamp(signStamp);
            claims.put(SecurityConstants.SIGN_STAMP, signStamp);
        }
        String token = JwtUtil.createJwt(claims, 7*24*60*60);
        // 更新登录信息
        refreshLogin(sysUser);
        loginSession.set(sysUser.getId(), loginUser);
        return SecurityConstants.BEARER + token;
    }

    private void refreshLogin(SysUser sysUser) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = request.getRemoteAddr();
        sysUser.setLoginIp(ipAddress);
        sysUser.setLoginTime(LocalDateTime.now());
        sysUserService.updateChain()
                .set(SysUserTable.SYS_USER.LOGIN_IP, sysUser.getLoginIp())
                .set(SysUserTable.SYS_USER.LOGIN_TIME, sysUser.getLoginTime())
                .where(SysUserTable.SYS_USER.ID.eq(sysUser.getId()))
                .update();
    }

}
