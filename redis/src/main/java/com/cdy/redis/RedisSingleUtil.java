package com.cdy.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

/**
 * redis 单机 工具类
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public class RedisSingleUtil implements RedisUtil {
    
    private JedisPool jPool;
    private JedisPoolConfig config;
    private final String host;
    private final Integer port;
    
    public RedisSingleUtil() {
        this("127.0.0.1", 6379);
    }
    
    
    public RedisSingleUtil(String host, Integer port) {
        this.host = host;
        this.port = port;
    }
    
    @Override
    public void init() {
        jPool = new JedisPool(host, port);
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
        try (Jedis jedis = jPool.getResource()) {
            String result = jedis.set(key, value);
            return result;
        }
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
            String result =  jedis.setex(key, time, value);
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
    public int size(String prefix) {
        try(Jedis jedis = jPool.getResource()){
            //todo 优化scan
            Set<String> keys = jedis.keys(prefix + "*");
            return keys.size();
        }
    }
    
    
    @Override
    public Jedis getJedis() {
        return jPool.getResource();
    }
    
    
    @Override
    public String blpop(int time, String... key) {
        List<String> blpop = null;
        try (Jedis jedis = jPool.getResource()) {
            blpop = jedis.blpop(time, key);
        }
        return blpop.get(1);
    }
    
    @Override
    public void rpush(String key, String... value) {
        try (Jedis jedis = jPool.getResource()) {
            jedis.rpush(key, value);
        }
    }
}
