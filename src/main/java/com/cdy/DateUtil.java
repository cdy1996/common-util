package com.cdy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * todo
 * Created by 陈东一
 * 2018/5/18 20:35
 */
public class DateUtil {
    
    public static Date string2Date(String date) throws ParseException {
        return string2Date(date, "yyyy-MM-dd");
    }
    
    public static Date string2Date(String date, String pattern) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.parse(date);
    }
    
    public static String format(Date date) throws ParseException {
        return format(date);
    }
    
    public static String format(String date) throws ParseException {
        return format(date);
    }
    
    public static String format(Date date, String pattern) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.format(date);
    }
    
    public static String format(String date, String pattern) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.format(string2Date(date));
    }
}
