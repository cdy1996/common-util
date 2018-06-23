package com.cdy.common.util;

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
     * 将实体类转成map对象，实体的属性名称为key，实体的属性值为value  ==>BeanUtils.describe(obj)
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
    
    
    /**
     * 将map转换为bean ==>BeanUtils.populate(clazz.getDeclaredConstructor().newInstance(), map);
     * @param map Map<String, Object
     * @param clazz Class
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     */
    public static void map2Bean(Map<String, Object> map, Class clazz) throws InvocationTargetException, IllegalAccessException, IntrospectionException, NoSuchMethodException, InstantiationException {
        Object obj = clazz.getDeclaredConstructor().newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (map.containsKey(key)) {
                Object value = map.get(key);
                // 得到property对应的setter方法
                Method setter = property.getWriteMethod();
                setter.invoke(obj, value);
            }
        }
    }
}
