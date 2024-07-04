package com.crud.dula.security.session;


import com.crud.dula.security.LoginUser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author crud
 * @date 2023/9/18
 */

public class DefaultSession implements LoginSession{

    public final static Map<Long, LoginUser> USER_MAP = new ConcurrentHashMap<>();

    @Override
    public LoginUser get(Long uid) {
        return USER_MAP.get(uid);
    }

    @Override
    public void set(Long uid, LoginUser user) {
        USER_MAP.put(uid, user);
    }

    @Override
    public void remove(Long uid) {
        USER_MAP.remove(uid);
    }

}
