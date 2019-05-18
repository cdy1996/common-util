package com.cdy.redis;

import redis.clients.jedis.Jedis;

/**
 * redis 工具类接口
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public interface RedisUtil {
    void init();
    
    Jedis getJedis();
    
    String blpop(int time, String... key);
    
    void rpush(String key, String... value);
    
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
