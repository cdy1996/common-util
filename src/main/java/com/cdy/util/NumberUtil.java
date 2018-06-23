package com.cdy.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字工具类
 * Created by 陈东一
 * 2018/5/23 21:21
 */
public class NumberUtil {
    
    private static String pattern = "0.00";
    private static int scale = 2;
    
    /**
     * 数字格式化 保留2为小鼠
     * @param num String
     * @return String
     */
    public static String format(String num){
        
        return format(num, pattern);
    }
    
    /**
     * 数字格式化
     * @param num String
     * @param pattern String
     * @return String
     */
    public static String format(String num, String pattern){
        return new DecimalFormat(pattern).format(num);
    }
    
    /**
     * 数字格式化 保留两位  四舍五入
     * @param bigDecimal BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal format(BigDecimal bigDecimal){
        
        
        return format(bigDecimal, scale);
    }
    
    /**
     * 数字格式化  四舍五入
     * @param bigDecimal BigDecimal
     * @param scale int  保留位数
     * @return BigDecimal
     */
    public static BigDecimal format(BigDecimal bigDecimal, int scale){
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 数字格式化 保留两位
     * @param d Double
     * @return BigDecimal
     */
    public static Double format(Double d){
        return format(new BigDecimal(d)).doubleValue();
    }
    
    /**
     * 数字格式化
     * @param d Double
     * @param scale int 保留位数
     * @return BigDecimal
     */
    public static Double format(Double d, int scale){
        return format(new BigDecimal(d), scale).doubleValue();
    }
    
}
