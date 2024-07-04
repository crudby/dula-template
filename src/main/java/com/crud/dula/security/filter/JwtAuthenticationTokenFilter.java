package com.crud.dula.security.filter;

import com.crud.dula.common.jwt.JwtUtil;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.security.LoginUser;
import com.crud.dula.security.SecurityConstants;
import com.crud.dula.security.exception.OffsiteLoginAuthenticationException;
import com.crud.dula.security.exception.TimeoutAuthenticationException;
import com.crud.dula.security.session.LoginSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author crud
 * @date 2023/9/15
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private LoginSession loginSession;

    @Value("${dula.single-active-session:false}")
    private Boolean singleActiveSession;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        String certificate = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (StringUtils.isNotBlank(certificate) && certificate.startsWith(SecurityConstants.BEARER)) {
            token = certificate.substring(SecurityConstants.BEARER.length());
        }else {
            token = Optional.ofNullable(request.getParameter(SecurityConstants.TOKEN)).map(Objects::toString).orElse("");
        }
        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 根据token获取用户信息
        LoginUser loginUser = this.getByToken(token, response);
        boolean checked = checkSign(loginUser, token, request, response);
        if (! checked) {
            return;
        }
        // 加载到上下文
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(userToken);
        filterChain.doFilter(request, response);
    }

    private LoginUser getByToken(String token, HttpServletResponse response) throws IOException, ServletException {
        LoginUser loginUser = null;
        // 正常取值
        Optional<Object> userId = JwtUtil.getSafeValue(token, SecurityConstants.USER_ID);
        if (userId.isPresent()) {
            loginUser = loginSession.get((Long) userId.get());
        } else if (JwtUtil.isSigned(token) && JwtUtil.inProtectedTime(token, SecurityConstants.PROTECT_TIME)) {
            // 保护期取值
            Optional<Object> optional = JwtUtil.getUnSafeValue(token, SecurityConstants.USER_ID);
            if (optional.isPresent()) {
                loginUser = loginSession.get((Long) optional.get());
                response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + JwtUtil.createNewToken(token));
            }
        }
        return loginUser;
    }

    public boolean checkSign(LoginUser loginUser, String token, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (Objects.isNull(loginUser)) {
            authenticationEntryPoint.commence(request, response, new TimeoutAuthenticationException(ResultCode.LOGIN_TIMEOUT.getMessage()));
            return false;
        }
        if (!singleActiveSession) {
            return true;
        }
        String signStamp = loginUser.getSignStamp();
        Optional<Object> optional = JwtUtil.getUnSafeValue(token, SecurityConstants.SIGN_STAMP);
        if (optional.isEmpty() || !Objects.equals(signStamp, optional.get())) {
            authenticationEntryPoint.commence(request, response, new OffsiteLoginAuthenticationException(ResultCode.OFFSITE_LOGIN.getMessage()));
            return false;
        }
        return true;
    }
}
