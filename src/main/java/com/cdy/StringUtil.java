package com.cdy;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * todo
 * Created by 陈东一
 * 2018/5/15 22:31
 */
public class StringUtil {
    
    public static Boolean isEmpty(String string){
        return StringUtils.isEmpty(string);
    }
    
    public static Boolean isNotEmpty(String string){
        return StringUtils.isNotEmpty(string);
    }
    
    public static Boolean isBlank(String string){
        return StringUtils.isBlank(string);
    }
    
    public static Boolean isNotBlank(String string){
        return StringUtils.isNotBlank(string);
    }
    
    public static Boolean contain(String str, String regex){
        Pattern pattern = Pattern.compile(regex);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }
}
