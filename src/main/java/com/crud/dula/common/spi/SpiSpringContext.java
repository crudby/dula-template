package com.crud.dula.common.spi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author crud
 * @date 2024/5/16
 */
@Slf4j
@Configuration
public class SpiSpringContext implements ApplicationContextAware {

    private static ApplicationContext APPLICATIONCONTEXT;

    private static void set(ApplicationContext applicationContext) {
        SpiSpringContext.APPLICATIONCONTEXT = applicationContext;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        set(applicationContext);
    }

    /**
     * 根据类型获取对象
     *
     * @param clz      clz类型
     * @param <T>具体的对象
     * @return 扩展点对象集合
     */
    public static <T> Collection<T> getSpi(Class<T> clz) {
        try {
            Map<String, T> beansOfType = APPLICATIONCONTEXT.getBeansOfType(clz);
            return beansOfType.values();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * 根据类型获取对象
     *
     * @param clz      clz类型
     * @param <T>具体的对象
     * @return 扩展点对象集合
     */
    public static <T> Collection<T> getSpiByPrefix(String prefix, Class<T> clz) {
        try {
            Map<String, T> beansOfType = APPLICATIONCONTEXT.getBeansOfType(clz);
            return beansOfType.entrySet().stream().filter(e -> e.getKey().startsWith(prefix)).map(Map.Entry::getValue).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * 根据类型获取对象
     *
     * @param clz      clz类型
     * @param <T>具体的对象
     * @return 扩展点对象集合
     */
    public static <T> T getOneSpi(Class<T> clz) {
        try {
            return APPLICATIONCONTEXT.getBean(clz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据类型获取对象
     *
     * @param clz      clz类型
     * @param <T>具体的对象
     * @return 扩展点对象集合
     */
    public static <T> T getSpi(String name, Class<T> clz) {
        try {
            Map<String, T> beansOfType = APPLICATIONCONTEXT.getBeansOfType(clz);
            return beansOfType.get(name);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
