package com.wuxin.blog.controller.admin.log;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.AccessLogService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/21:42
 * @Description:
 */
@RestController
@RequestMapping("/admin/access/log")
public class AdminAccessLogController {

    @Autowired
    AccessLogService accessLogService;

    @OperationLogger("查看访问日志")
    @PostMapping("/list")
    public Result findAccessLog(@RequestBody PageVo pageVo)
    {
        return Result.ok(accessLogService.selectListByPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords(), pageVo.getStart(), pageVo.getEnd()));
    }

    @OperationLogger("删除一条访问日志")
    @RequiresRoles(value = "root")
    @DeleteMapping("/del")
    public Result delAccessLog(@RequestParam("id") Long id)
    {
        accessLogService.delete(id);
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }

    @OperationLogger("删除全部访问日志")
    @RequiresRoles(value = "root")
    @DeleteMapping("/del/all")
    public Result delAllAccessLog()
    {
        accessLogService.deleteAll();
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }

    @OperationLogger("批量删除访问日志")
    @DeleteMapping("/del/part")
    @RequiresRoles(value = "root")
    public Result delPartAccessLog(@RequestParam("ids") List<Long> ids)
    {
        accessLogService.batchDelete();
        return Result.ok("该接口还未实现哦");
    }
}
