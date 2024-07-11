package com.crud.dula.common.utils;

import com.crud.dula.common.result.BizException;
import com.crud.dula.common.result.ResultCode;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author crud
 * @date 2023/10/11
 */
@Slf4j
public class BeanUtil {

    private static volatile Validator validator;

    public static Validator getValidator() {
        if (validator == null) {
            synchronized (BeanUtil.class) {
                if (validator == null) {
                    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
                    validator = validatorFactory.getValidator();
                    validatorFactory.close();
                }
            }
        }
        return validator;
    }

    /**
     * 校验参数是否合法
     *
     * @param t 参数对象
     */
    public static <T> void checkParamsValid(T t) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(t);
        if (Objects.nonNull(violations) && !violations.isEmpty()) {
            String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            throw new BizException(ResultCode.PARAM_ERROR.code, message);
        }
    }

    /**
     * 将对象转换为指定类型的实例。
     *
     * @param r 源对象
     * @param tClass 目标类型
     * @return 转换后的目标类型实例
     */
    public static <R, T> T toBean(R r, Class<T> tClass) {
        return toBean(r, tClass, (r1, t) -> {});
    }

    /**
     * 将对象转换为指定类型的实例，支持自定义转换器。
     *
     * @param r 源对象
     * @param tClass 目标类型
     * @param biConsumer 自定义转换器，用于执行额外的转换逻辑
     * @return 转换后的目标类型实例
     */
    public static <R, T> T toBean(R r, Class<T> tClass, BiConsumer<R, T> biConsumer) {
        return convertSingle(r, tClass, biConsumer);
    }

    /**
     * 将列表中的对象转换为指定类型的列表。
     *
     * @param rList 源对象列表
     * @param tClass 目标类型
     * @return 转换后的目标类型列表
     */
    public static <R, T> List<T> toList(List<R> rList, Class<T> tClass) {
        return toList(rList, tClass, (r1, t) -> {});
    }

    /**
     * 将列表中的对象转换为指定类型的列表，支持自定义转换器。
     *
     * @param rList 源对象列表
     * @param tClass 目标类型
     * @param biConsumer 自定义转换器，用于执行额外的转换逻辑
     * @return 转换后的目标类型列表
     */
    public static <R, T> List<T> toList(List<R> rList, Class<T> tClass, BiConsumer<R, T> biConsumer) {
        return convertCollection(rList, tClass, biConsumer);
    }

    /**
     * 将分页对象中的记录转换为指定类型的分页对象。
     *
     * @param pageInfo 源分页对象
     * @param tClass 目标类型
     * @return 转换后的目标类型分页对象
     */
    public static <R, T> Page<T> toPage(Page<R> pageInfo, Class<T> tClass) {
        return toPage(pageInfo, tClass, (r1, t) -> {});
    }

    /**
     * 将分页对象中的记录转换为指定类型的分页对象，支持自定义转换器。
     *
     * @param pageInfo 源分页对象
     * @param tClass 目标类型
     * @param biConsumer 自定义转换器，用于执行额外的转换逻辑
     * @return 转换后的目标类型分页对象
     */
    public static <R, T> Page<T> toPage(Page<R> pageInfo, Class<T> tClass, BiConsumer<R, T> biConsumer) {
        if (pageInfo == null) {
            throw new IllegalArgumentException("PageInfo cannot be null");
        }
        List<R> records = pageInfo.getRecords();
        if (Objects.nonNull(records) && !records.isEmpty()) {
            List<T> list = convertCollection(records, tClass, biConsumer);
            return new Page<>(list, pageInfo.getPageNumber(), pageInfo.getPageSize(), pageInfo.getTotalRow());
        }
        return new Page<>(new ArrayList<>(), pageInfo.getPageNumber(), pageInfo.getPageSize(), pageInfo.getTotalRow());
    }

    /**
     * 单个对象转换逻辑实现，通过反射创建目标类型实例，并复制源对象属性至目标对象。
     *
     * @param r 源对象
     * @param tClass 目标类型
     * @param biConsumer 自定义转换器，用于执行额外的转换逻辑
     * @return 转换后的目标类型实例
     */
    private static <R, T> T convertSingle(R r, Class<T> tClass, BiConsumer<R, T> biConsumer) {
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T t = constructor.newInstance();
            BeanUtils.copyProperties(r, t);
            if (Objects.nonNull(biConsumer)) {
                biConsumer.accept(r, t);
            }
            return t;
        } catch (Exception e) {
            throw new BizException("Conversion failed", e);
        }
    }

    /**
     * 集合对象转换逻辑实现，遍历源对象列表，分别调用 {@link #convertSingle(Object, Class, BiConsumer)} 进行单个对象的转换。
     *
     * @param rList 源对象列表
     * @param tClass 目标类型
     * @param biConsumer 自定义转换器，用于执行额外的转换逻辑
     * @return 转换后的目标类型列表
     */
    private static <R, T> List<T> convertCollection(List<R> rList, Class<T> tClass, BiConsumer<R, T> biConsumer) {
        if (rList == null || rList.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            ArrayList<T> list = new ArrayList<>(rList.size());
            for (R r : rList) {
                T t = convertSingle(r, tClass, biConsumer);
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            throw new BizException("Collection conversion failed", e);
        }
    }

}
