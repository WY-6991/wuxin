package com.wuxin.docker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: wuxin001
 * @Date: 2022/02/18/12:46
 * @Description:
 */
@Configuration
public class RedisConfig {

    // @Autowired
    // private RedisTemplate<String, Object> redisTemplate;


    // @Bean
    // public RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory connectionFactory) {
    //     redisTemplate.setConnectionFactory(connectionFactory);
    //     StringRedisSerializer serializer = new StringRedisSerializer();
    //     redisTemplate.setKeySerializer(serializer);
    //     redisTemplate.setValueSerializer(serializer);
    //     redisTemplate.setHashKeySerializer(serializer);
    //     redisTemplate.setHashValueSerializer(serializer);
    //
    //     redisTemplate.afterPropertiesSet();
    //     return redisTemplate;
    // }


}
