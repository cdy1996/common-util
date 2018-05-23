package com.cdy;

import java.text.DecimalFormat;

/**
 * todo
 * Created by 陈东一
 * 2018/5/23 21:21
 */
public class NumberUtil {

    public static String format(String num){
        return format(num, "0.00");
    }
    
    public static String format(String num, String pattern){
        return new DecimalFormat(pattern).format(num);
    }
    
}
