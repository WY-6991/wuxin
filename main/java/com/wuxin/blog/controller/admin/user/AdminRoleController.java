package com.wuxin.blog.controller.admin.user;

import com.wuxin.blog.annotation.AccessLimit;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Role;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.RoleService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.string.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;



    /**
     * 角色列表
     * @param pageVo Page
     * @return page
     */

    @AccessLimit(seconds = 60, limitCount = 10, msg = "操作频率过高！一分钟之后再试！")
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
        if (StringUtils.isNotNull(roleByName)) {
            return Result.error("该角色已存在");
        }
        roleService.add(role);
        return Result.ok("添加成功！");
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
        return Result.ok("修改成功");
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
        return Result.ok("删除成功");
    }
}
