package com.wuxin.blog.controller.admin.user;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Role;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.RoleService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/15:59
 * @Description: 角色管理
 */
@RestController
@RequestMapping("/admin/role")
public class AdminRoleController {

    @Resource
    private RoleService roleService;

    /**
     * 角色列表
     * @param pageVo Page
     * @return page
     */
    @OperationLogger("查看角色列表")
    @PostMapping("/list")
    public Result findRole(@RequestBody PageVo pageVo) {
        return Result.ok(roleService.selectListByPage(pageVo.getCurrent(), pageVo.getLimit()));
    }

    /**
     * 添加权限
     * @param role roleDTO
     * @return success
     */
    @OperationLogger("添加角色")
    @RequiresRoles("root")
    @PostMapping("/add")
    public Result roleAdd(@RequestBody Role role) {
        Role roleByName = roleService.findRoleByName(role.getRoleName());
        if (roleByName != null) {
            return Result.error("该角色已存在");
        }
        roleService.add(role);
        return Result.ok(Message.ADD_SUCCESS.getMessage());
    }

    /**
     * 修改角色权限名
     * @param role roleDTO
     * @return success
     */
    @OperationLogger("修改角色")
    @RequiresRoles("root")
    @GetMapping("/update")
    public Result roleUpdate(@RequestBody Role role) {
        Role roleByName = roleService.findRoleByName(role.getRoleName());
        if (roleByName != null) {
            return Result.error("该角色已存在");
        }
        roleService.update(role);
        return Result.ok(Message.UPDATE_SUCCESS.getMessage());
    }

    /**
     * 删除角色
     * @param roleId roleID
     * @return success
     */
    @OperationLogger("删除角色")
    @RequiresRoles("root")
    @GetMapping("/del/{roleId}")
    public Result roleDel(@PathVariable("roleId") Long roleId) {
        roleService.delete(roleId);
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }
}
