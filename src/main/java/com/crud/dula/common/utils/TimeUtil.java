package com.crud.dula.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 时间处理类
 *
 * @author crud
 * @date 2024/4/19
 */
public class TimeUtil {

    /**
     * 默认日期格式字符串。
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式字符串。
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    /**
     * 默认日期时间格式字符串。
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认月份格式字符串。
     */
    public static final String DEFAULT_MONTH_FORMAT = "yyyy-MM";

    /**
     * 默认年份格式字符串。
     */
    public static final String DEFAULT_YEAR_FORMAT = "yyyy";

    /**
     * 将LocalDateTime转换为时间戳。
     *
     * @param localDateTime 要转换的LocalDateTime对象。
     * @return 转换后的时间戳。
     */
    public static Long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 使用默认日期时间格式解析时间戳为LocalDateTime。
     *
     * @param time 待解析的时间。
     * @return 解析后的LocalDateTime对象。
     */
    public static LocalDateTime parse(Long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 使用默认日期时间格式解析字符串为LocalDateTime。
     *
     * @param time 待解析的时间字符串。
     * @return 解析后的LocalDateTime对象。
     */
    public static LocalDateTime parse(String time) {
        return parse(time, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 使用指定格式解析字符串为LocalDateTime。
     *
     * @param time    待解析的时间字符串。
     * @param pattern 时间字符串的格式模式。
     * @return 解析后的LocalDateTime对象。
     */
    public static LocalDateTime parse(String time, String pattern) {
        // 根据提供的模式解析时间字符串为LocalDateTime
        return LocalDateTime.from(DateTimeFormatter.ofPattern(pattern).parse(time));
    }

    /**
     * 使用默认日期格式解析字符串为LocalDate。
     *
     * @param time 待解析的日期字符串。
     * @return 解析后的LocalDate对象。
     */
    public static LocalDate parseDate(String time) {
        return parseDate(time, DEFAULT_DATE_FORMAT);
    }

    /**
     * 使用指定格式解析字符串为LocalDate。
     *
     * @param time    待解析的日期字符串。
     * @param pattern 日期字符串的格式模式。
     * @return 解析后的LocalDate对象。
     */
    public static LocalDate parseDate(String time, String pattern) {
        // 根据提供的模式解析日期字符串为LocalDate
        return LocalDate.from(DateTimeFormatter.ofPattern(pattern).parse(time));
    }

    /**
     * 使用默认日期时间格式格式化LocalDateTime为字符串。
     *
     * @param localDateTime 要格式化的LocalDateTime实例。
     * @return 格式化后的日期时间字符串。
     */
    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 使用指定格式格式化LocalDateTime为字符串。
     *
     * @param localDateTime 要格式化的LocalDateTime实例。
     * @param pattern 用于格式化的模式字符串。
     * @return 格式化后的日期时间字符串。
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 使用默认日期格式格式化LocalDate为字符串。
     *
     * @param localDate 要格式化的LocalDate实例。
     * @return 格式化后的日期字符串。
     */
    public static String format(LocalDate localDate) {
        return format(localDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 使用指定格式格式化LocalDate为字符串。
     *
     * @param localDate 要格式化的LocalDate实例。
     * @param pattern 用于格式化的模式字符串。
     * @return 格式化后的日期字符串。
     */
    public static String format(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取指定日期的月份起始和结束日期。
     *
     * @param localDate 指定日期。
     * @return 包含起始日期和结束日期的数组。
     */
    public static LocalDate[] getMonthRange(LocalDate localDate) {
        LocalDate start = localDate.withDayOfMonth(1);
        LocalDate end = localDate.withDayOfMonth(localDate.lengthOfMonth());
        return new LocalDate[]{start, end};
    }

    /**
     * 获取指定日期的年度起始和结束日期。
     *
     * @param localDate 指定日期。
     * @return 包含起始日期和结束日期的数组。
     */
    public static LocalDate[] getYearRange(LocalDate localDate) {
        LocalDate start = localDate.withMonth(1).withDayOfMonth(1);
        LocalDate end = localDate.withMonth(12).withDayOfMonth(31);
        return new LocalDate[]{start, end};
    }

}
