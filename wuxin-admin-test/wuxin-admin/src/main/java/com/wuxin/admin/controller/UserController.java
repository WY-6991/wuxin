package com.wuxin.admin.controller;

import com.wuxin.admin.service.UserPermissionService;
import com.wuxin.admin.service.UserRoleService;
import com.wuxin.admin.service.UserService;
import com.wuxin.admin.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/11:24
 * @Description:
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserPermissionService userPermissionService;

    @GetMapping("/list")
    public Result userList(){
        return Result.ok(userService.list());
    }

    @GetMapping("/role/list")
    public Result userRoleList(@RequestParam("userId")Long userId){
        return Result.ok(userRoleService.selectUserRoleVo(userId));
    }

    @GetMapping("/permission/list")
    public Result selectUserPermissionsByUserId(@RequestParam("userId")Long userId){
        return Result.ok(userPermissionService.selectUserPermissionVo(userId));
    }

}
