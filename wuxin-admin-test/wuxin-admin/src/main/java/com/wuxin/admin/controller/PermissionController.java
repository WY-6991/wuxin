package com.wuxin.admin.controller;

import com.wuxin.admin.pojo.Permission;
import com.wuxin.admin.service.PermissionService;
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
@RequestMapping("/permission")
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    public Result permissions(){
        return Result.ok(permissionService.list());
    }
}
