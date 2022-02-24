package com.wuxin.admin.controller;

import com.wuxin.admin.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/11:11
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/hello")
    public String test01() {
        return "test 01";
    }

    @GetMapping("/hello1")
    public Result test2() {
        return Result.ok().put("hello", "content");
    }

    @GetMapping("/redis")
    public Result test03() {
        return Result.ok();
    }
}
