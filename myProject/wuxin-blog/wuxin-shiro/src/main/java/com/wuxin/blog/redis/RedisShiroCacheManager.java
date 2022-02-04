package com.wuxin.blog.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * https://github.com/alexxiyang/shiro-redis/tree/master/src/main/java/org/crazycake/shiro
 * @Author: wuxin001
 * @Date: 2022/01/11/21:04
 * @Description: shiro集成redis
 */
public class RedisShiroCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String cacheManagerName) throws CacheException {
        System.out.println("redis 缓存启动中==========>"+cacheManagerName);
        return new RedisCache<>();
    }
}
