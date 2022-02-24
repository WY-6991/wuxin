package com.wuxin.admin.controller;

import com.wuxin.admin.pojo.Role;
import com.wuxin.admin.service.RoleService;
import com.wuxin.admin.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/12:58
 * @Description:
 */
@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    private Result roleList(){
        return Result.ok(roleService.list());
    }
}
