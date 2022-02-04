package com.wuxin.blog.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/02/04/18:02
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String test01(){
        return "hello controller 01";
    }
}
