package com.wuxin.docker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/02/18/12:48
 * @Description:
 */
@RestController
public class HelloController {



    @GetMapping("/hello")
    public String hello() {
        return "当前有用户访问了";
    }
}
