package com.cdy.cache;

/**
 * 缓存工具类
 * Created by 陈东一
 * 2018/8/25 22:24
 */
public interface CacheUtil {
    
    /**
     * 设置会过期的值
     * @param key
     * @param value
     * @param time
     * @return
     */
    String set(String key, String value, int time);
    
    /**
     * 获取值
     * @param key
     * @return
     */
    String get(String key);
    
    
    /**
     * 删除key
     * @param key
     */
    void delete(String... key);
    
    
    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);
    
    
    /**
     * 设置失效时间
     * @param key
     * @param time
     */
    void expire(String key, int time);
    
    /**
     * 判断key的值是否存在
     * @param key
     * @return
     */
    boolean exist(String key);
    
    /**
     * 根据前缀查询缓存数量
     * @param prefix
     * @return
     */
    int size(String prefix);
}
