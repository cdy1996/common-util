package com.cdy.common.util.cache.redis;

import com.cdy.common.util.cache.CacheUtil;

/**
 * redis 工具类接口
 * Created by 陈东一
 * 2018/5/20 15:16
 */
public interface JedisUtil extends CacheUtil {
    void init();
}