package com.cdy;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * todo
 * Created by 陈东一
 * 2018/5/15 22:33
 */
public class CollectionUtil {

    public static Boolean isEmpty(Collection collection){
        return CollectionUtils.isEmpty(collection);
    }
    
    public static Boolean isNotEmpty(Collection collection){
        return CollectionUtils.isNotEmpty(collection);
    }
    
    @SafeVarargs
    public static <T> List<T> of(T... t){
        return new ArrayList<T>(Arrays.asList(t));
    }
    
    
}
