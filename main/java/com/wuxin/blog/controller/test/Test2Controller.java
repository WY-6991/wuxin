package com.wuxin.blog.controller.test;

import com.wuxin.blog.annotation.RepeatSubmit;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.security.MySecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/01/11/10:40
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class Test2Controller {

    @GetMapping("/get/login/user/info")
    public Result getUser(){
        // 测试从工具类中获取登录用户信息
        return Result.ok(MySecurityUtils.getUser());
    }


    /**
     * 测试限流处理
     * @return
     */
    @RepeatSubmit
    @GetMapping("/access/limit")
    public Result accessLimit(){
        return Result.ok("hello");
    }
}
