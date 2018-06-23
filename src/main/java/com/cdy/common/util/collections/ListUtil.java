package com.cdy.common.util.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 列表工具类
 * Created by 陈东一
 * 2018/5/18 20:17
 */
public class ListUtil {
    
    /**
     * 生成list
     * @param t t
     * @param <T> T
     * @return List<T>
     */
    @SafeVarargs
    public static <T> List<T> of(T... t){
        return new ArrayList<T>(Arrays.asList(t));
    }
    
    /**
     * 判断list是否为空
     * @param list List
     * @return Boolean
     */
    public static Boolean isEmpty(List list){
        return CollectionUtil.isEmpty(list);
    }
    
    /**
     * 判断list是否不为空
     * @param list List
     * @return Boolean
     */
    public static Boolean isNotEmpty(List list){
        return CollectionUtil.isNotEmpty(list);
    }
    
    
}
