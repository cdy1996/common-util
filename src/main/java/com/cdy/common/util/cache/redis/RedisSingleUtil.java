package com.cdy.common.util.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 单机 工具类
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public class RedisSingleUtil implements RedisUtil {
    
    private JedisPool jPool;
    private JedisPoolConfig config;
    private final String host;
    private final int port;
    private final int expireTime;             //默认失效时间
    
    public RedisSingleUtil() {
        this(60 * 60, "127.0.0.1", 6379);
    }
    
    
    public RedisSingleUtil(int time, String host, int port) {
        this.expireTime = time;
        this.host = host;
        this.port = port;
    }
    
    public RedisSingleUtil(String host, int port) {
        this(60 * 60, host, port);
    }
    
    
    @Override
    public void init() {
        jPool = new JedisPool(host, port);
    }
    
    
    public static void main(String[] args) {
        RedisSingleUtil redisUtil = new RedisSingleUtil("192.168.2.101", 6001);
        redisUtil.init();
        String name = redisUtil.get("name");
        System.out.println(name);
    }
    
    /**
     * 添加，失效时间60*60
     *
     * @param key   String
     * @param value String
     * @return String
     */
    @Override
    public String set(String key, String value) {
        return set(key, value, expireTime);
    }
    
    /**
     * 添加
     *
     * @param key   String
     * @param value String
     * @param time  int
     * @return String
     */
    @Override
    public String set(String key, String value, int time) {
        try (Jedis jedis = jPool.getResource()) {
            String result = jedis.set(key, value);
            jedis.expire(key, time);
            return result;
        }
    }
    
    /**
     * 获取
     *
     * @param key String
     * @return String
     */
    @Override
    public String get(String key) {
        try (Jedis jedis = jPool.getResource()) {
            return jedis.get(key);
        }
    }
    
    /**
     * 删除
     *
     * @param key String
     */
    @Override
    public void delete(String... key) {
        try (Jedis jedis = jPool.getResource()) {
            jedis.del(key);
        }
    }
    
    /**
     * 设置失效时间
     *
     * @param key  String
     * @param time int
     */
    @Override
    public void expire(String key, int time) {
        try (Jedis jedis = jPool.getResource()) {
            jedis.del(key);
        }
    }
    
    @Override
    public boolean exist(String key) {
        try (Jedis jedis = jPool.getResource()) {
            return jedis.exists(key);
        }
    }
    
    
    @Override
    public Jedis getJedis() {
        return jPool.getResource();
    }
}
