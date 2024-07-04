package com.crud.dula.security.service;

import com.crud.dula.security.LoginUser;

/**
 * @author crud
 * @date 2023/9/14
 */
public interface WechatService {

    /**
     * 获取用户信息
     * @param code code
     * @return loginUser
     */
    LoginUser loginByCode(String code);
}
