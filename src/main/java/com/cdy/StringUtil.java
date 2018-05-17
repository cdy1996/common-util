package com.cdy;

import org.apache.commons.lang.StringUtils;

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
}
