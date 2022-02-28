package com.wuxin.docker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/02/28/11:36
 * @Description:
 */
@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/hello")
    public String test(){
        Long increment = redisTemplate.opsForValue().increment("test-count-people", 1);
        return "当前访问人数:"+increment;
    }



}
