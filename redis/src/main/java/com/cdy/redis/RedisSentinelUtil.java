package com.cdy.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis 主从 工具类
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public class RedisSentinelUtil implements RedisUtil {
    
    private JedisSentinelPool jedisPool;
    private JedisPoolConfig config;
    private final Set<String> sentinels = new HashSet<>();
    private final String masterName;
    private final String password;
    
    public RedisSentinelUtil() {
        this(new HashSet<>(), null, null);
    }
    
    
    public RedisSentinelUtil(Set<String> sentinels, String masterName, String password) {
        this.sentinels.addAll(sentinels);
        this.masterName = masterName;
        this.password = password;
    }
    
    public void addSentinels(String host, String port) {
        sentinels.add(host + ":" + port);
    }
    
    
    @Override
    public void init() {
        config = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        config.setMaxIdle(8);
        // 最大连接数, 默认8个
        config.setMaxTotal(8);
        //最小空闲连接数, 默认0
        config.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
//        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
//        jedisPoolConfig.setTestOnBorrow(true);
        if (password == null || password.equals("")) {
            jedisPool = new JedisSentinelPool(masterName, sentinels, config);
        } else {
            jedisPool = new JedisSentinelPool(masterName, sentinels, config, password);
        }
    }
    
    
    @Override
    public String get(String key) {
        String value = null;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.get(key);
        }
        return value;
    }
    
    @Override
    public void delete(String... key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }
    
    @Override
    public void expire(String key, int time) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.expire(key, time);
        }
    }
    
    @Override
    public boolean exist(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }
    
    @Override
    public int size(String prefix) {
        try (Jedis jedis = jedisPool.getResource()) {
            //todo 优化scan
            Set<String> keys = jedis.keys(prefix + "*");
            return keys.size();
        }
    }
    
    
    @Override
    public String set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String set = jedis.set(key, value);
            return set;
        }
    }
    
    @Override
    public String set(String key, String value, int time) {
        try (Jedis jedis = jedisPool.getResource()) {
            String set = jedis.set(key, value);
            jedis.expire(key, time);
            return set;
        }
    }
    
    public byte[] get(byte[] key) {
        byte[] value;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.get(key);
        }
        return value;
    }
    
    public void set(byte[] key, byte[] value, int time) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
            jedis.expire(key, time);
        }
    }
    
    public void hset(byte[] key, byte[] field, byte[] value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, field, value);
        }
    }
    
    public void hset(String key, String field, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, field, value);
        }
    }
    
    public String hget(String key, String field) {
        String value = null;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.hget(key, field);
        }
        
        return value;
    }
    
    public byte[] hget(byte[] key, byte[] field) {
        byte[] value = null;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.hget(key, field);
        }
        
        return value;
    }
    
    public void hdel(byte[] key, byte[] field) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hdel(key, new byte[][]{field});
        }
    }
    
    public void lpush(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(key, new String[]{value});
        }
    }
    
    public void rpush(byte[] key, byte[] value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush(key, new byte[][]{value});
        }
    }
    
    public void rpoplpush(byte[] key, byte[] destination) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpoplpush(key, destination);
        }
    }
    
    public List<byte[]> lpopList(byte[] key) {
        List list = null;
        try (Jedis jedis = jedisPool.getResource()) {
            list = jedis.lrange(key, 0L, -1L);
        }
        return list;
    }
    
    public String rpop(String key) {
        String bytes = null;
        try (Jedis jedis = jedisPool.getResource()) {
            bytes = jedis.rpop(key);
        }
        
        return bytes;
    }
    
    public List<byte[]> lrange(byte[] key, int from, int to) {
        List result = null;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.lrange(key, from, to);
        }
        return result;
    }
    
    
    public long llen(byte[] key) {
        long len = 0L;
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.llen(key);
        }
        return len;
    }
    
    
    @Override
    public Jedis getJedis() {
        return jedisPool.getResource();
    }
    
    @Override
    public String blpop(int time, String... key) {
        List<String> blpop = null;
        try (Jedis jedis = jedisPool.getResource()) {
            blpop = jedis.blpop(time, key);
        }
        return blpop.get(1);
    }
    
    @Override
    public void rpush(String key, String... value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush(key, value);
        }
    }
    
}
