package com.cdy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * todo
 * Created by 陈东一
 * 2018/5/18 20:17
 */
public class ListUtil {
    
    @SafeVarargs
    public static <T> List<T> of(T... t){
        return new ArrayList<T>(Arrays.asList(t));
    }
    
    public static Boolean isEmpty(List list){
        return CollectionUtil.isEmpty(list);
    }
    
    public static Boolean isNotEmpty(List list){
        return CollectionUtil.isNotEmpty(list);
    }
    
    
}
