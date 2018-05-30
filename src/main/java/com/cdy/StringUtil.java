package com.cdy;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by 陈东一
 * 2018/5/15 22:31
 */
public class StringUtil {
    
    /**
     * 判断是否为空
     * @param string String
     * @return Boolean
     */
    public static boolean isEmpty(String string){
        return StringUtils.isEmpty(string);
    }
    
    /**
     * 判断是否不为空
     * @param string String
     * @return Boolean
     */
    public static boolean isNotEmpty(String string){
        return StringUtils.isNotEmpty(string);
    }
    
    /**
     * 判断是否为空白
     * @param string String
     * @return boolean
     */
    public static boolean isBlank(String string){
        return StringUtils.isBlank(string);
    }
    
    /**
     * 判断是否为不空白
     * @param string String
     * @return boolean
     */
    public static boolean isNotBlank(String string){
        return StringUtils.isNotBlank(string);
    }
    
    /**
     * 正则检查是否匹配
     * @param string String
     * @param regex String
     * @return boolean
     */
    public static boolean match(String string, String regex){
        Pattern pattern = Pattern.compile(regex);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }
    
    
    public static String toString(Object o){
        return ReflectionToStringBuilder.toString(o);
    }
}
