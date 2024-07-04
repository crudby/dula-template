package com.crud.dula.common.utils;

import com.crud.dula.common.result.JacksonObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author crud
 * @date 2024/5/13 11:18
 */
@Slf4j
public class JsonUtil {


    private final static ObjectMapper OBJECT_MAPPER = new JacksonObjectMapper();

    /**
     * json to object
     *
     * @param json  json
     * @param clazz clazz
     * @param <T>   T
     * @return Optional
     */
    public static <T> Optional<T> toBean(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json) || Objects.isNull(clazz)) {
            log.warn("param is blank. ");
            return Optional.empty();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(OBJECT_MAPPER.readValue(json, clazz));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return Optional.empty();
        }
    }


    public static <T> Optional<T> toBean(Object json, Class<T> clazz) {
        if (Objects.isNull(json) || Objects.isNull(clazz)) {
            log.warn("param is blank. ");
            return Optional.empty();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(OBJECT_MAPPER.convertValue(json, clazz));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> toBean(Object json, TypeReference<T> clazz) {
        if (Objects.isNull(json)) {
            log.warn("param is blank. ");
            return Optional.empty();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(OBJECT_MAPPER.convertValue(json, clazz));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * json to object
     *
     * @param json  json
     * @param clazz clazz
     * @param <T>   T
     * @return Optional
     */
    public static <T> Optional<T> toBean(String json, TypeReference<T> clazz) {
        if (StringUtils.isBlank(json)) {
            log.warn("param is blank. ");
            return Optional.empty();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(OBJECT_MAPPER.readValue(json, clazz));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * json to object
     *
     * @param json      json
     * @param reference clazz
     * @param <T>       T
     * @return Optional
     */
    public static <T> List<T> toList(String json, TypeReference<List<T>> reference) {
        if (StringUtils.isBlank(json)) {
            log.warn("param is blank. ");
            return Collections.emptyList();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return OBJECT_MAPPER.readValue(json, reference);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * json to object
     *
     * @param json     json
     * @param beanType beanType
     * @param <T>      T
     * @return Optional
     */
    public static <T> List<T> toList(String json, Class<T> beanType) {
        if (StringUtils.isBlank(json)) {
            log.warn("param is blank. ");
            return Collections.emptyList();
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * json to object
     *
     * @param obj      obj
     * @param beanType beanType
     * @param <T>      T
     * @return Optional
     */
    public static <T> List<T> toList(Object obj, Class<T> beanType) {
        if (Objects.isNull(obj)) {
            log.warn("param is blank. ");
            return Collections.emptyList();
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return OBJECT_MAPPER.convertValue(obj, javaType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * map to object
     *
     * @param params params
     * @param clazz  clazz
     * @param <T>    T
     * @return Optional
     */
    public static <T> Optional<T> toBean(Map<String, Object> params, Class<T> clazz) {
        if (Objects.isNull(params) || params.isEmpty()) {
            log.warn("param is blank. ");
            return Optional.empty();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(OBJECT_MAPPER.convertValue(params, clazz));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }


    /**
     * 将对象转换层map
     *
     * @param e   转换的对象
     * @param <E> 类型
     * @return map
     */
    public static <E> Optional<Map<String, Object>> toMap(E e) {
        if (Objects.isNull(e)) {
            log.warn("param is blank. ");
            return Optional.empty();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(OBJECT_MAPPER.convertValue(e, new MapTypeReference()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return Optional.empty();
        }
    }

    /**
     * 将对象转换层map
     *
     * @param e 转换的对象
     * @return map
     */
    @SuppressWarnings("ALL")
    public static Optional<Map<String, Object>> toMap(String e) {
        if (Objects.isNull(e)) {
            log.warn("param is blank. ");
            return Optional.empty();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(OBJECT_MAPPER.readValue(e, Map.class));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return Optional.empty();
        }
    }

    /**
     * 将对象转换层map
     *
     * @param e 转换的对象
     * @return map
     */
    @SuppressWarnings("ALL")
    public static Optional<Map<String, Object>> toMap(byte[] bytes) {
        if (Objects.isNull(bytes)) {
            log.warn("param is blank. ");
            return Optional.empty();
        }
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(OBJECT_MAPPER.readValue(bytes, Map.class));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return Optional.empty();
        }
    }


    /**
     * object to json
     *
     * @param <T> T t
     * @param t   参数类型
     * @return string
     */
    public static <T> String toJson(T t) {
        return toJson(t, false);
    }

    /**
     * object to json
     *
     * @param <T> T t
     * @param t   参数类型
     * @return string
     */
    public static <T> String toJson(T t, boolean format) {
        if (Objects.isNull(t)) {
            log.warn("param is blank. ");
            return "";
        }
        try {
            if (format) {
                return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(t);
            }
            return OBJECT_MAPPER.writeValueAsString(t);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }


    /**
     * map 类型
     */
    private static class MapTypeReference extends TypeReference<Map<String, Object>> {
        private MapTypeReference() {
        }
    }
}
