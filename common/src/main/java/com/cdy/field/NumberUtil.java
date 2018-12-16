package com.cdy.field;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字工具类
 * Created by 陈东一
 * 2018/5/23 21:21
 */
public class NumberUtil {
    
    public static String PATTERN_0 = "0";
    public static String PATTERN_2 = "0.00";
    public static int SCALE_0 = 0;
    public static int SCALE_2 = 2;
    
    /**
     * 数字格式化 保留2为小鼠
     * @param num String
     * @return String
     */
    public static String format(String num){
        return format(new BigDecimal(num), PATTERN_0);
    }
    
    public static String format(Double num){
        return format(new BigDecimal(num), PATTERN_0);
    }
    
    public static String format(Integer num){
        return format(new BigDecimal(num), PATTERN_0);
    }
    
    /**
     * 数字格式化
     * @param num String
     * @param pattern String
     * @return String
     */
    public static String format(BigDecimal num, String pattern){
        return new DecimalFormat(pattern).format(num);
    }
    
    
    
   
    /**
     * 数字格式化 保留两位  四舍五入
     * @param num
     * @return BigDecimal
     */
    public static BigDecimal rounding(Double num){
        return rounding(new BigDecimal(num), SCALE_0);
    }
    
    public static BigDecimal rounding(Integer num){
        return rounding(new BigDecimal(num), SCALE_0);
    }
    
    public static BigDecimal rounding(String num){
        return rounding(new BigDecimal(num), SCALE_0);
    }
    
    /**
     * 数字格式化  四舍五入
     * @param bigDecimal BigDecimal
     * @param scale int  保留位数
     * @return BigDecimal
     */
    public static BigDecimal rounding(BigDecimal bigDecimal, int scale){
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    

    
}
