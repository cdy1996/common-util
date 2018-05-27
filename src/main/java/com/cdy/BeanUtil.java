package com.cdy;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * bean工具类
 * Created by 陈东一
 * 2018/5/15 22:32
 */
public class BeanUtil {
    
    /**
     * 将实体类转成map对象，实体的属性名称为key，实体的属性值为value
     * @param obj Object
     * @return Map<String, Object>
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> bean2Map(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            // 过滤class属性
            if (!"class".equals(key)) {
                // 得到property对应的getter方法
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                
                map.put(key, value);
            }
        }
        return map;
        
    }
}
