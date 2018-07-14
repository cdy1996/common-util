package com.cdy.common.util.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * Created by 陈东一
 * 2018/5/18 20:35
 */
public class DateUtil {
    
    public static String PATTERN_YMD = "yyyy-MM-dd";
    public static String PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * yyyy：年
     * MM：月
     * dd：日
     * hh：1~12小时制(1-12)
     * HH：24小时制(0-23)
     * mm：分
     * ss：秒
     * S：毫秒
     * E：星期几
     * D：一年中的第几天
     * F：一月中的第几个星期(会把这个月总共过的天数除以7)
     * w：一年中的第几个星期
     * W：一月中的第几星期(会根据实际情况来算)
     * a：上下午标识
     * k：和HH差不多，表示一天24小时制(1-24)。
     * K：和hh差不多，表示一天12小时制(0-11)。
     * z：表示时区
     */
    
    /**
     * 字符串转为日期，接受的格式为yyyy-MM-dd
     * @param date String
     * @return Date
     * @throws ParseException
     */
    public static Date convent(String date) throws ParseException {
        return convent(date, PATTERN_YMD);
    }
    
    /**
     * 将字符串转为日期
     * @param date String
     * @param pattern String 指定接受的格式
     * @return Date
     * @throws ParseException
     */
    public static Date convent(String date, String pattern) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.parse(date);
    }
    
    /**
     * 格式化时间，格式为yyyy-MM-dd
     * @param date Date
     * @return String
     */
    public static String toString(Date date) {
        return toString(date, PATTERN_YMD);
    }
 
    
    /**
     * 格式化时间
     * @param date Date
     * @param pattern String 格式类型
     * @return String
     */
    public static String toString(Date date, String pattern) {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.format(date);
    }
    
   
}
