package com.jhome.springconfig.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by wangmin on 2018/9/8.
 */
public class CustomRedisCacheManager implements CacheManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new CustomCache<K, V>(1800, redisTemplate);
    }
}
