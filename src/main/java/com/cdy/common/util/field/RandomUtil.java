package com.cdy.common.util.field;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * 随机工具类
 * Created by 陈东一
 * 2018/5/30 22:57
 */
public class RandomUtil {
    
    public static String verCoder(){
        Random random = new Random();
        return StringUtils.substring(String.valueOf(random.nextInt()),2,6);
    }
    
    
    public static String randomNo() {
        Random random = new Random();
        return String.valueOf(Math.abs(random.nextInt()*-10));
    }
}
