package com.cdy.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * Created by 陈东一
 * 2018/5/18 20:35
 */
public class DateUtil {
    private static String pattern = "yyyy-MM-dd";
    
    
    /**
     * 字符串转为日期，接受的格式为yyyy-MM-dd
     * @param date String
     * @return Date
     * @throws ParseException
     */
    public static Date string2Date(String date) throws ParseException {
        
        return string2Date(date, pattern);
    }
    
    /**
     * 将字符串转为日期
     * @param date String
     * @param pattern String 指定接受的格式
     * @return Date
     * @throws ParseException
     */
    public static Date string2Date(String date, String pattern) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.parse(date);
    }
    
    /**
     * 格式化时间，格式为yyyy-MM-dd
     * @param date Date
     * @return String
     * @throws ParseException
     */
    public static String format(Date date) throws ParseException {
        return format(date, pattern);
    }
    
    /**
     * 格式化时间，格式为yyyy-MM-dd
     * @param date String
     * @return
     * @throws ParseException
     */
    public static String format(String date) throws ParseException {
        return format(date, pattern);
    }
    
    /**
     * 格式化时间
     * @param date Date
     * @param pattern String 格式类型
     * @return
     * @throws ParseException
     */
    public static String format(Date date, String pattern) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.format(date);
    }
    
    /**
     * 格式化时间
     * @param date String
     * @param pattern String 格式类型
     * @return
     * @throws ParseException
     */
    public static String format(String date, String pattern) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.format(string2Date(date));
    }
}
