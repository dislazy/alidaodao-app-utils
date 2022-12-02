package com.alidaodao.app.utils;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Stream;

/**
 * 关于日期时间的一些工具处理
 * 选取了最可能日常应用的方法
 * 也没必要再添加其他更多的转化
 * 当需要获取类似当前周 或月的范围及日期时可以添加对应的方法
 * @author jack
 */
public class DateUtils implements Serializable {

    /**
     * 默认月份格式
     */
    public static final String MONTH_DEFAULT_FORMAT = "yyyy-MM";

    /**
     * 默认日期格式
     */
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

    /**
     * 其他日期格式
     */
    public static final String DATE_BASE_FORMAT = "yyyy/MM/dd";

    /**
     * 默认日期时间格式
     */
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 时间序列格式
     */
    public static final String DATETIME_SERIAL_FORMAT = "yyyyMMddHHmmss";
    /**
     * 取默认的系统zone id
     */
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();
    /**
     * 转换LocalDate为Date
     * @param localDate 日期
     * @return 响应结果
     */
    public static Date localDateToUtilDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 转换LocalDateTime为Date
     *
     * @param localDateTime 日期
     * @return 响应结果
     */
    public static Date localDateTimeToUtilDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 转换Date为LocalDateTime
     *
     * @param utilDate date
     * @return 响应结果
     */
    public static LocalDateTime utilDateToLocalDateTime(Date utilDate) {
        return LocalDateTime.ofInstant(utilDate.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 计算两个日期中的天数
     *
     * @param temporal1Inclusive 开始日期
     * @param temporal2Exclusive 结束日期
     * @return 响应结果
     */
    public static long daysBetween(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return ChronoUnit.DAYS.between(temporal1Inclusive, temporal2Exclusive);
    }

    /**
     * 计算两个日期中的小时数
     *
     * @param temporal1Inclusive 开始时间
     * @param temporal2Exclusive 结束时间
     * @return 响应结果
     */
    public static long hoursBetween(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return ChronoUnit.HOURS.between(temporal1Inclusive, temporal2Exclusive);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param parse
     * @return
     */
    public static String parse(LocalDate date,String parse){
        return date.format(DateTimeFormatter.ofPattern(parse));
    }

    /**
     * 计算两个日期中的分钟数
     *
     * @param temporal1Inclusive 开始时间
     * @param temporal2Exclusive 结束时间
     * @return 响应结果
     */
    public static long minutesBetween(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return ChronoUnit.MINUTES.between(temporal1Inclusive, temporal2Exclusive);
    }

    /**
     * 计算两个日期中的秒数
     *
     * @param temporal1Inclusive 开始时间
     * @param temporal2Exclusive 结束时间
     * @return 响应结果
     */
    public static long secondsBetween(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return ChronoUnit.SECONDS.between(temporal1Inclusive, temporal2Exclusive);
    }

    /**
     * 日期时间格式化
     *
     * @param dateTime 日期时间
     * @param format   格式
     * @return 响应结果
     */
    public static String dateTimeFormat(LocalDateTime dateTime, String format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 日期格式化
     *
     * @param date   日期
     * @param format 格式
     * @return 响应结果
     */
    public static String dateFormat(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 时间格式化
     *
     * @param time   时间
     * @param format 格式
     * @return 响应结果
     */
    public static String timeFormat(LocalTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 转化String为日期时间
     *
     * @param text   日期内容
     * @param format 格式
     * @return 响应结果
     */
    public static LocalDateTime parseToLocalDateTime(String text, String format) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 转化String为日期
     *
     * @param text   日期内容
     * @param format 格式
     * @return 响应结果
     */
    public static LocalDate parseToLocalDate(String text, String format) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 转化String为时间
     *
     * @param text   时间内容
     * @param format 格式
     * @return 响应结果
     */
    public static LocalTime parseToLocalTime(String text, String format) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获取两个日期之间的所有天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 响应结果
     */
    public static List<String> getDayRegions(String start, String end) {
        List<String> times = new ArrayList<String>();
        try {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            long distance = ChronoUnit.DAYS.between(startDate, endDate);
            if (distance < 1L) {
                times.add(startDate.toString());
                return times;
            }
            Stream.iterate(startDate, d -> d.plusDays(1L)).limit(distance + 1L).forEach(f -> times.add(f.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 获取日期中的周一
     *
     * @param date 日期
     * @return 响应结果
     */
    public static String getCurrWeekMonday(String date) {
        if (Objects.isNull(date) || date.length() == 0) {
            return null;
        }
        try {
            LocalDate beginDateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate monday = beginDateTime.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1L);
            return monday.format(DateTimeFormatter.ofPattern(DATE_DEFAULT_FORMAT));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取日期中的周日
     *
     * @param date 日期
     * @return 响应结果
     */
    public static String getCurrWeekSunDay(String date) {
        if (Objects.isNull(date) || date.length() == 0) {
            return null;
        }
        try {
            LocalDate beginDateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate monday = beginDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1L);
            return monday.format(DateTimeFormatter.ofPattern(DATE_DEFAULT_FORMAT));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串日期转换为时间戳
     * @param text
     * @return
     */
    public static long stringToMillisecond(String text) {
        long milli = 0;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_MINUTE_FORMAT);
            LocalDateTime localDateTime1 = LocalDateTime.parse(text, formatter);
            milli = LocalDateTime.from(localDateTime1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            return 0;
        }
        return milli;
    }


    /**
     * 日期时间转化为时间戳
     *
     * @param datetime 日期时间
     * @return 响应结果
     */
    public static long localDateTimeToMillisecond(LocalDateTime datetime) {
        return datetime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 日期转化为时间戳
     *
     * @param date 日期
     * @return 响应结果
     */
    public static long localDateToMillisecond(LocalDate date) {
        return date.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }

    /**
     * 时间戳转化为日期时间
     *
     * @param timestamp 时间戳
     * @return 响应结果
     */
    public static LocalDateTime millisecondToLocalDateTime(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    /**
     * 时间戳转化为日期
     *
     * @param timestamp 时间戳
     * @return 响应结果
     */
    public static LocalDate millisecondToLocalDate(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }


    /**
     * 根据给定parrern从字符串解析出Date
     *
     * @param pattern 格式
     * @param text    日期字符串
     * @return 日期
     */
    public static Date parse(String pattern, String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dest;
        // 仅判断结尾的dd 一定隐含了部分特殊pattern解析不能的错误
        // 权且先这样处理 有待改进
        String dateEndWith = "dd";
        if (pattern.trim().endsWith(dateEndWith)) {
            dest = LocalDate.parse(text, formatter).atStartOfDay();
        } else {
            dest = LocalDateTime.parse(text, formatter);
        }

        return Date.from(dest.atZone(ZONE_ID).toInstant());
    }

    /**
     * 从字符串解析出Date
     *
     * @param text
     * @return
     */
    public static Date parseDate(String text) {
        return parse(DATE_DEFAULT_FORMAT, text);
    }

    /**
     * 从字符串解析出Date
     *
     * @param text
     * @return
     */
    public static Date parseDateTime(String text) {
        return parse(DATETIME_DEFAULT_FORMAT, text);
    }

    /**
     * 根据给定parrern从Date解析出字符串
     *
     * @param pattern 格式
     * @param date    日期
     * @return 日期字符串
     */
    public static String format(String pattern, Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dest = LocalDateTime.ofInstant(date.toInstant(), ZONE_ID);

        return formatter.format(dest);
    }

    /**
     * 日期格式化
     * @param  date
     * @return  java.lang.String
     */
    public static String formatDate(Date date) {
        return date == null ? null : format(DATE_DEFAULT_FORMAT, date);
    }

    /**
     * 时间格式化
     * @param  date
     * @return  java.lang.String
     */
    public static String formatDateTime(Date date) {
        return format(DATETIME_DEFAULT_FORMAT, date);
    }

    /**
     * 根据给定parrern解析当前日期
     *
     * @param pattern 格式
     * @return 当前日期字符串
     */
    public static String formatNow(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 根据默认DATE_DEFAULT_FORMAT解析当前日期
     *
     * @return 当前日期字符串
     */
    public static String dateNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_DEFAULT_FORMAT);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 根据默认DATETIME_DEFAULT_FORMAT解析当前日期
     *
     * @return 当前日期字符串
     */
    public static String dateTimeNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_DEFAULT_FORMAT);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 截止当前时间，获取上个月的时间：年月日
     * @return
     */
    public static String lastMonthDate() {
        Date amountMonth = addDay(localDateToUtilDate(LocalDate.now()), -30);
        return formatDate(amountMonth);
    }

    /**
     * 日期添加n天
     *
     * @return
     */
    public static Date addDay(Date date,int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }


    /**
     * 获取偏移月份  再指定日期
     *
     * @param date
     * @param amountMonth 月份偏移量 正整数 往后 负整数 往前
     * @param assignDay   指定日期
     * @return
     */
    public static Date getAmountMonthAssignDay(Date date,int amountMonth, int assignDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amountMonth);
        calendar.set(Calendar.DAY_OF_MONTH, assignDay);
        return calendar.getTime();
    }



    /**
     * 比较日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Long dateDifference(Date beginDate, Date endDate) {
        beginDate = parseDate(formatDate(beginDate));
        endDate = parseDate(formatDate(endDate));
        return (endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24);
    }

    public static List<LocalDate> getDayRegions(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> times = new ArrayList<>();
        try {
            long distance = ChronoUnit.DAYS.between(startDate, endDate);
            if (distance < 1L) {
                times.add(startDate);
                return times;
            }
            Stream.iterate(startDate, d -> d.plusDays(1L)).limit(distance + 1L).forEach(f -> times.add(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static LocalDate utilDateToLocalDate(Date utilDate) {
        return LocalDateTime.ofInstant(utilDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }


    /**
     * 获取开始时间和结束时间的月份
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> queryMonthRegions(LocalDate startDate, LocalDate endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate) || startDate.isAfter(endDate)) {
            return new ArrayList<>();
        }
        //这样是为了防止日期不是1号
        startDate = startDate.withDayOfMonth(1);
        endDate = endDate.withDayOfMonth(1);
        List<String> monthRegion = new ArrayList<>();

        monthRegion.add(parse(startDate, MONTH_DEFAULT_FORMAT));
        while (startDate.isBefore(endDate)) {
            startDate = startDate.plusMonths(1);
            monthRegion.add(parse(startDate, MONTH_DEFAULT_FORMAT));
        }
        return monthRegion;
    }

}