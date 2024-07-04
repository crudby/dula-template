package com.crud.dula.security.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author crud
 * @date 2023/9/18
 */
@Slf4j
@Configuration
public class SessionAutoConfig {

    /**
     * 配置redis则使用redis缓存
     *
     * @param stringRedisTemplate stringRedisTemplate
     * @return redisSession
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.redis", name = "host")
    public RedisSession redisSession(StringRedisTemplate stringRedisTemplate) {
        log.info("System session type: redis");
        return new RedisSession(stringRedisTemplate);
    }

    /**
     * 默认使用内存缓存
     *
     * @return defaultSession
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisSession")
    public DefaultSession defaultSession() {
        log.info("System session type: memory");
        return new DefaultSession();
    }

}
