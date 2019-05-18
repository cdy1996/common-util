package com.cdy;

import com.cdy.redis.RedisSentinelUtil;
import com.cdy.redis.RedisSingleUtil;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class RedisTest {
    
    @Test
    public void testRedis() {
        
        RedisSingleUtil redisUtil = new RedisSingleUtil("192.168.2.101", 6001);
        redisUtil.init();
        String name = redisUtil.get("name");
        System.out.println(name);
    }
    
    @Test
    public void testSentinel() {
        
        RedisSentinelUtil redisUtil = new RedisSentinelUtil();
        redisUtil.addSentinels("192.168.2.101", "26379");
        redisUtil.addSentinels("192.168.2.101", "26479");
        redisUtil.init();
        String name = redisUtil.get("name");
        System.out.println(name);
    }
}
