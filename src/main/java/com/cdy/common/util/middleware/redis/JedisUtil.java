package com.cdy.common.util.middleware.redis;

/**
 * redis 工具类接口
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public interface  JedisUtil{
    
    void init();
    
    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);
    
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
    void delete(String ...key);
    
    /**
     * 设置失效时间
     * @param key
     * @param time
     */
    void expire(String key, int time);
}
