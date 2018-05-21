package com.cdy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * todo
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public class JedisUtil {
    
    public static JedisPool jPool;
    
    static{
        jPool = new JedisPool("localhost",6379);
    }
    
    public static String set(String key, String value){
        Jedis jedis = null ;
        try {
            jedis = jPool.getResource();
            String result = jedis.set(key, value);
            jedis.expire(key, 60*60);
            return result;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
    
    public static String set(String key, String value, int time){
        Jedis jedis = null ;
        try {
            jedis = jPool.getResource();
            String result = jedis.set(key, value);
            jedis.expire(key, time);
            return result;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
    
    public static String get(String key) {
        Jedis jedis = null ;
        try {
            jedis =  jPool.getResource();
            return jedis.get(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
    
    public static void delete(String key) {
        Jedis jedis = null ;
        try {
            jedis =  jPool.getResource();
            jedis.del(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
    
    
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
