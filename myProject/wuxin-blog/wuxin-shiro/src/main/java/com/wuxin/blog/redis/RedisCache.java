package com.wuxin.blog.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * 使用redis-cache实现权限控制
 *
 * @Author: wuxin001
 * @Date: 2022/01/12/0:25
 * @Description: redis cache
 */
@Component
public class RedisCache<K, V> implements Cache<K, V> {

    @Autowired
    private RedisService redisService;

    private final String keyPrefix = "";
    private final int expire = 0;

    private static final String USER_ROLES = "user-roles";
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);


    private String key;

    @Override
    public V get(K key) throws CacheException {
        logger.info("redis cache get:{}", key);


        return null;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.info("redis cache put:key{},value:{}", key, value);

        return value;
    }

    @Override
    public V remove(K k) throws CacheException {

        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        logger.info("set redis:{}");
        return null;
    }

    @Override
    public Collection<V> values() {
        logger.info("collection redis:{}");
        return null;
    }


}
