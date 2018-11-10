package com.cdy.common.util.cache.redis;

import com.cdy.common.util.cache.CacheUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * redis 工具类接口
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public interface RedisUtil extends CacheUtil {
    void init();
    
    Jedis getJedis();
    
    List<String> blpop(String... key);
    
    void rpush(String key, String... value);
}
