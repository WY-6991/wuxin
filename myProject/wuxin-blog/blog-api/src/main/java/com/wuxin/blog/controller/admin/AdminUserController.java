package com.wuxin.blog.controller.admin;

import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/12:27
 * @Description:
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result getUserList(){
        return Result.ok(userService.list());
    }
}
