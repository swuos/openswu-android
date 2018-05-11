package com.gallops.mobile.jmvclibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期转换工具类
 * Created by wangyu on 2017/6/26.
 */

public class DateUtils {

    public static final long MINUTE = 60 * 1000L;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long WEEK = DAY * 7;
    public static final long MONTH = DAY * 30;
    public static final long YEAR = MONTH * 12;

    /**
     * 获取时间字符串对应的毫秒数
     *
     * @param dateString   时间字符串
     * @param formatString 自定义格式，例如 yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static long getTimeMillisecondByDateStringWithFormatString(String dateString, String formatString) {
        long result = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString, Locale.getDefault());
        try {
            result = dateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取毫秒数对应的自定义格式的时间字符串
     *
     * @param milliseconds 毫秒数
     * @param formatString 自定义格式，例如 yyyy年MM月dd日
     * @return
     */
    public static String getDateStringByMillisecondsWithFormatString(long milliseconds, String formatString) {
        Date date = new Date(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 获取时间字符串对应的毫秒数
     *
     * @param dateString 时间字符串
     * @return
     */
    public static long getTimeMillisecondsByYMD(String dateString) {
        return getTimeMillisecondByDateStringWithFormatString(dateString, "yyyy-MM-dd");
    }

    /**
     * 获取毫秒数对应的时间字符串
     *
     * @param milliseconds 毫秒数
     * @return
     */
    public static String getYMDByMilliseconds(long milliseconds) {
        return getDateStringByMillisecondsWithFormatString(milliseconds, "yyyy-MM-dd");
    }

    /**
     * 得到几天前的日期
     *
     * @param days         几天前
     * @param formatString 字符串格式
     */
    public static String getFewDaysAgo(int days, String formatString) {
        long millisecond = System.currentTimeMillis() - (days * 24 * 3600 * 1000);
        Date date = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 得到几天前的日期
     *
     * @param days 几天前
     */
    public static String getFewDaysAgo(int days) {
        return getFewDaysAgo(days, "yy-MM-dd");
    }

    /**
     * 判断两个时间是否在一天内
     *
     * @param firstDate  基本日期
     * @param secondDate 比较日期
     * @return 如果secondDate与firstDate在一天内返回true，否则返回false
     */
    public static boolean isSameDay(long firstDate, long secondDate) {
        boolean result = false;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String firstDateFormat = format.format(new Date(firstDate));
        String secondDateFormat = format.format(new Date(secondDate));
        if (firstDateFormat.equals(secondDateFormat))
            result = true;
        return result;
    }

    /**
     * 判断两个时间是否在一周内
     *
     * @param firstDate  基本日期
     * @param secondData 比较日期
     * @return
     */
    public static boolean isSameWeek(long firstDate, long secondData) {
        String date1 = getYMDByMilliseconds(firstDate);
        String date2 = getYMDByMilliseconds(secondData);
        long date1Second = getTimeMillisecondsByYMD(date1);
        long date2Second = getTimeMillisecondsByYMD(date2);
        return Math.abs(date1Second - date2Second) < WEEK;
    }

    /**
     * 获取时长毫秒
     *
     * @param driveMillis
     * @return
     */
    public static String getTimeDuration(long driveMillis) {
        String result;
        long driveMinute = driveMillis / 1000 / 60;
        if (driveMinute < 60) {
            result = driveMinute + "分钟";
        } else {
            int hour = (int) (driveMinute / 60);
            int minute = (int) (driveMinute % 60);
            if (minute != 0) {
                result = hour + "小时" + (driveMinute % 60) + "分钟";
            } else {
                result = hour + "小时";
            }
        }
        return result;
    }

    /**
     * 获取当天凌晨时间
     *
     * @return
     */
    public static long getToDayTime() {
        long currentTime = System.currentTimeMillis();
        String toDay = getDateStringByMillisecondsWithFormatString(currentTime, "yyyy-MM-dd");
        return getTimeMillisecondByDateStringWithFormatString(toDay, "yyyy-MM-dd");
    }

    /**
     * Gets cuurnt year.获取当前年份
     *
     * @return the cuurnt year
     */
    public static int getCurrntYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Gets cuurnt year.获取当前年份
     *
     * @return the cuurnt year
     */
    public static int getCurrntMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }
}
