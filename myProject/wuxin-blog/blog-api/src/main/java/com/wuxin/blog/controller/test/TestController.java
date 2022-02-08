package com.wuxin.blog.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/12:05
 * @Description:
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello01() {
        return "hello";
    }
}
