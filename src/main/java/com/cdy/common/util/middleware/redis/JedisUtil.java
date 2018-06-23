package com.cdy.common.util.middleware.redis;

/**
 * redis工具类
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public interface  JedisUtil{
    
    int redis_max_total = 10; //最大连接数, 默认8个
    int redis_max_idle = 10; //最大空闲连接数, 默认8个
    int redis_expire_time = 60 * 60 * 24; //默认失效时间1天
    
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
