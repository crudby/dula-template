package com.crud.dula.security.session;

import com.alibaba.fastjson.JSON;
import com.crud.dula.security.LoginUser;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author crud
 * @date 2023/9/18
 */
public class RedisSession implements LoginSession{

    private final StringRedisTemplate stringRedisTemplate;

    public RedisSession(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public LoginUser get(Long uid) {
        return JSON.parseObject(stringRedisTemplate.opsForValue().get(String.valueOf(uid)), LoginUser.class);
    }

    @Override
    public void set(Long uid, LoginUser user) {
        stringRedisTemplate.opsForValue().set(String.valueOf(uid), JSON.toJSONString(user), 7, TimeUnit.DAYS);
    }

    @Override
    public void remove(Long uid) {
        stringRedisTemplate.delete(String.valueOf(uid));
    }
}
