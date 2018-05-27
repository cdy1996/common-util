package com.cdy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis工具类
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public class JedisUtil {
    
    public static JedisPool jPool;
    
    static{
        jPool = new JedisPool("localhost",6379);
    }
    
    /**
     * 添加，失效时间60*60
     * @param key String
     * @param value String
     * @return String
     */
    public static String set(String key, String value){
        return set(key, value, 60 * 60);
    }
    
    /**
     * 添加
     * @param key String
     * @param value String
     * @param time int
     * @return String
     */
    public static String set(String key, String value, int time){
        try (Jedis jedis = jPool.getResource()) {
            String result = jedis.set(key, value);
            jedis.expire(key, time);
            return result;
        }
    }
    
    /**
     * 获取
     * @param key String
     * @return String
     */
    public static String get(String key) {
        try (Jedis jedis = jPool.getResource()) {
            return jedis.get(key);
        }
    }
    
    /**
     * 删除
     * @param key String
     */
    public static void delete(String key) {
        try (Jedis jedis = jPool.getResource()) {
            jedis.del(key);
        }
    }
    
    /**
     * 设置失效时间
     * @param key String
     * @param time int
     */
    public static void expire(String key, int time){
        Jedis jedis = null ;
        try {
            jedis = jPool.getResource();
            jedis.expire(key, time);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
