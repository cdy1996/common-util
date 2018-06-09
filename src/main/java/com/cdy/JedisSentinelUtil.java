package com.cdy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis工具类
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public class JedisSentinelUtil {
    
    private static JedisSentinelPool jedisPool;
    private static JedisPoolConfig config;
    private static int time = 60 * 60;
    private static String localhost = "127.0.0.1";
    private static int port = 6379;
    private static int redis_max_total = 10; //最大连接数, 默认8个
    private static int redis_max_idle = 10; //最大空闲连接数, 默认8个
    
    static{
    
        config = new JedisPoolConfig();
        config.setMaxTotal(redis_max_total);
        config.setMaxIdle(redis_max_idle);
        config.setMaxWaitMillis(time);
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        config.setTestOnBorrow(true);
    
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.2.101:26379");
//        sentinels.add("192.168.2.101:26479");
        jedisPool = new JedisSentinelPool(
                "mymaster", sentinels, config,"123456");
    }
    
    public static void main(String[] args) {
        String name = get("name");
        System.out.println(name);
    }
    
    
    public static String get(String key) {
        String value = null;
        try (Jedis jedis = jedisPool.getResource()) {
            String s = jedis.info();
            System.out.println(s);
            value = jedis.get(key);
        }
        return value;
    }
    
    public static byte[] get(byte[] key) {
        byte[] value;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.get(key);
        }
        return value;
    }
    
    public static void set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }
    
    public static void set(String key, String value, int time) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
            jedis.expire(key, time);
        }
    }
    
    public static void set(byte[] key, byte[] value, int time) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
            jedis.expire(key, time);
        }
    }
    
    public static void hset(byte[] key, byte[] field, byte[] value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, field, value);
        }
    }
    
    public static void hset(String key, String field, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, field, value);
        }
    }
    
    public static String hget(String key, String field) {
        String value = null;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.hget(key, field);
        }
        
        return value;
    }
    
    public static byte[] hget(byte[] key, byte[] field) {
        byte[] value = null;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.hget(key, field);
        }
        
        return value;
    }
    
    public static void hdel(byte[] key, byte[] field) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hdel(key, new byte[][]{field});
        }
    }
    
    public static void lpush(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(key, new String[]{value});
        }
    }
    
    public static void rpush(byte[] key, byte[] value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush(key, new byte[][]{value});
        }
    }
    
    public static void rpoplpush(byte[] key, byte[] destination) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpoplpush(key, destination);
        }
    }
    
    public static List<byte[]> lpopList(byte[] key) {
        List list = null;
        try (Jedis jedis = jedisPool.getResource()) {
            list = jedis.lrange(key, 0L, -1L);
        }
        return list;
    }
    
    public static String rpop(String key) {
        String bytes = null;
        try (Jedis jedis = jedisPool.getResource()) {
            bytes = jedis.rpop(key);
        }
        
        return bytes;
    }
    
    public static List<byte[]> lrange(byte[] key, int from, int to) {
        List result = null;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.lrange(key, from, to);
        }
        return result;
    }
    
    public static void del(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }
    
    public static long llen(byte[] key) {
        long len = 0L;
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.llen(key);
        }
        return len;
    }
    
}
