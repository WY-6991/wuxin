package com.wuxin.blog.controller.admin.log;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/21:43
 * @Description:
 */
@RestController
@RequestMapping("/admin/login/log")
public class AdminLoginController {

    @Autowired
    private LoginLogService loginLogService;

    @OperationLogger("获取登录日志")
    @PostMapping("/list")
    public Result findLoginLog(@RequestBody PageVo pageVo)
    {
        return Result.ok(loginLogService.selectListByPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords(), pageVo.getStart(), pageVo.getEnd()));
    }

    @RequiresRoles(value = "root")
    @OperationLogger("删除登录日志")
    @DeleteMapping("/del")
    public Result delLoginLogById(@RequestParam("id") Long id)
    {
        loginLogService.delete(id);
        return Result.ok("删除成功！");
    }

    @OperationLogger("删除全部登录日志")
    @RequiresRoles(value = "root")
    @DeleteMapping("/del/all")
    public Result delAllLoginLog()
    {
        loginLogService.deleteAll();
        return Result.ok("删除成功！");

    }

    @OperationLogger("批量删除登录日志")
    @RequiresRoles(value = "root")
    @DeleteMapping("/del/part")
    public Result delPartLoginLogByIds(@RequestParam("ids") List<Long> ids)
    {
        loginLogService.batchDelete();
        return Result.ok("删除成功！");

    }
}
