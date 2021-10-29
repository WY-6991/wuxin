package com.wuxin.blog.controller.admin;

import com.wuxin.blog.pojo.Role;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.RoleService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 角色列表
     * @return
     */
    @PostMapping("/list")
    public Result roleList(@RequestBody PageVo pageVo){
        return R.ok(roleService.findRole(pageVo.getCurrent(),pageVo.getLimit()));
    }

    /**
     * 添加权限
     * @param role roleDTO
     * @return success
     */
    @PostMapping("/add")
    public Result roleAdd(@RequestBody Role role){
        Role roleByName = roleService.findRoleByName(role.getRoleName());
        if(roleByName !=null) return R.error("该角色已存在");
        if(roleService.addRole(role) == 1) return R.ok("添加成功");
        return R.ok("添加失败");
    }

    /**
     * 修改角色权限名
     * @param role roleDTO
     * @return success
     */
    @GetMapping("/update")
    public Result roleUpdate(@RequestBody Role role){
        Role roleByName = roleService.findRoleByName(role.getRoleName());
        if(roleByName !=null) return R.error("该角色已存在");
        if(roleService.updateRole(role)) return R.ok("修改成功");
        return R.ok("修改失败");
    }

    /**
     * 删除角色
     * @param roleId roleID
     * @return success
     */
    @GetMapping("/del/{roleId}")
    public Result roleDel(@PathVariable("roleId") Long roleId){
        if(roleService.delRole(roleId) == 1) return R.ok("删除成功！");
        return R.ok("删除失败！");
    }
}
