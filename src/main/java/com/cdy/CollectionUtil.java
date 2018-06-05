package com.cdy;

import java.util.Collection;

/**
 * 集合工具类
 * Created by 陈东一
 * 2018/5/15 22:33
 */
public class CollectionUtil {
    
    /**
     * 判断集合是否为空
     * @param collection Collection
     * @return Boolean
     */
    public static Boolean isEmpty(Collection collection){
        return (collection == null || collection.isEmpty());
    }
    
    /**
     * 判断集合是否不为空
     * @param collection Collection
     * @return Boolean
     */
    public static Boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }
    
  
}
