package com.cdy;

import java.math.BigDecimal;
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
    
    public static BigDecimal format(BigDecimal bigDecimal){
        return format(bigDecimal, 2);
    }
    
    public static BigDecimal format(BigDecimal bigDecimal, int scale){
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    public static Double format(Double d){
        return format(new BigDecimal(d)).doubleValue();
    }
    
    public static Double format(Double d, int scale){
        return format(new BigDecimal(d), scale).doubleValue();
    }
    
}
