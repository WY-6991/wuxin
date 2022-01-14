package com.wuxin.blog.shiro.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 *
 * @Author: wuxin001
 * @Date: 2022/01/12/0:25
 * @Description: redis cache
 */
@Component
public class RedisCache<K,V> implements Cache<K,V> {
    @Override
    public V get(K k) throws CacheException {
        System.out.println("redis cache k="+k);
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        System.out.println("=============redis put cache k=========="+k+"v="+v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        System.out.println("=====================redis cache remove==================="+k);

        return null;
    }

    @Override
    public void clear() throws CacheException {
        System.out.println("=====================clear===================");

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        System.out.println("=-================redis set key===========");
        return null;
    }

    @Override
    public Collection<V> values() {
        System.out.println("=-================Collection===========");
        return null;
    }
}
