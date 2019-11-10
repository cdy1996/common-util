package com.cdy.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis 集群
 * Created by 陈东一
 * 2019/11/10 19:50
 */
public class RedisClusterUtil implements RedisUtil {
    
    private JedisCluster jedis;
    private JedisPoolConfig jedisPoolConfig;
    private final String[] hosts;
    private final int[] ports;
    private final String password;
    
    
    public RedisClusterUtil(String[] hosts, int[] ports) {
        this(hosts, ports, null);
    }
    
    
    public RedisClusterUtil(String[] hosts, int[] ports, String password) {
        this.hosts = hosts;
        this.ports = ports;
        this.password = password;
    }
    
    
    @Override
    public void init() {
        // Jedis连接池配置
        this.jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(8);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(8);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
//        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
//        jedisPoolConfig.setTestOnBorrow(true);
        // 添加集群的服务节点Set集合
        Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
        // 添加节点
        for (int i = 0; i < hosts.length; i++) {
            hostAndPortsSet.add(new HostAndPort(hosts[i], ports[i]));
        }
        if (password == null || password.equals("")) {
            jedis = new JedisCluster(hostAndPortsSet, 3000, 3000, 3, jedisPoolConfig);
        } else {
            jedis = new JedisCluster(hostAndPortsSet, 3000, 3000, 3, password, jedisPoolConfig);
    
        }
        
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
        String result = jedis.set(key, value);
        return result;
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
        String result = jedis.setex(key, time, value);
        return result;
    }
    
    /**
     * 获取
     *
     * @param key String
     * @return String
     */
    @Override
    public String get(String key) {
        return jedis.get(key);
    }
    
    /**
     * 删除
     *
     * @param key String
     */
    @Override
    public void delete(String... key) {
        jedis.del(key);
    }
    
    /**
     * 设置失效时间
     *
     * @param key  String
     * @param time int
     */
    @Override
    public void expire(String key, int time) {
        jedis.del(key);
    }
    
    @Override
    public boolean exist(String key) {
        return jedis.exists(key);
    }
    
    @Override
    public int size(String prefix) {
        throw new UnsupportedOperationException();
    }
    
    
    @Override
    public Jedis getJedis() {
        throw new UnsupportedOperationException();
    }
    
    
    @Override
    public String blpop(int time, String... key) {
        List<String> blpop = null;
        blpop = jedis.blpop(time, key);
        return blpop.get(1);
    }
    
    @Override
    public void rpush(String key, String... value) {
        jedis.rpush(key, value);
    }
}
