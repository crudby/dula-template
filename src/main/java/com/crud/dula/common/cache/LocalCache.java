package com.crud.dula.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author crud
 * @date 2024/4/29
 */
@Component
public class LocalCache {

    /**
     * 缓存
     */
    private final Cache<String, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    /**
     * 获取缓存
     *
     * @param name     缓存KEY
     * @param supplier 获取方法
     * @param <T>      缓存对象类型
     * @return 缓存对象
     */
    public <T> T load(String name, Supplier<Optional<T>> supplier) {
        Object object = cache.getIfPresent(name);

        if (Objects.isNull(supplier)) {
            return (T) object;
        }

        // 如果数据为空
        if (Objects.isNull(object)) {
            Optional<T> optional = supplier.get();
            if (optional.isPresent()) {
                T obj = optional.get();
                cache.put(name, obj);
                return obj;
            }
        }
        return (T) object;
    }

    /**
     * 删除缓存
     *
     * @param key 缓存KEY
     */
    public void expireKey(String key) {
        cache.invalidate(key);
    }

    /**
     * 获取缓存
     *
     * @param name 缓存KEY
     * @param <T>  缓存对象类型
     * @return 缓存对象
     */
    public <T> T get(String name) {
        Object object = cache.getIfPresent(name);
        if (Objects.isNull(object)) {
            return null;
        }
        return (T) object;
    }

}
