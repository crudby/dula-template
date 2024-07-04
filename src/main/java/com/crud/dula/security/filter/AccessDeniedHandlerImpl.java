package com.crud.dula.security.filter;

import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.result.ResultUtil;
import com.crud.dula.common.result.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author crud
 * @date 2023/9/15
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResultUtil.writeResponseResult(response, HttpResult.fail(ResultCode.AUTHORIZATION_DENIED));
    }
}
