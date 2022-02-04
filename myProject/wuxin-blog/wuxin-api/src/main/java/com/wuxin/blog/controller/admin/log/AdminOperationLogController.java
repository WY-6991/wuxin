package com.wuxin.blog.controller.admin.log;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.OperationLogService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/21:45
 * @Description:
 */
@RestController
@RequestMapping("/admin/operation/log")
public class AdminOperationLogController {
    
    @Autowired 
    private OperationLogService operationLogService;

    @OperationLogger("获取操作日志列表")
    @PostMapping("/list")
    public Result findOperationLog(@RequestBody PageVo pageVo)
    {
        return Result.ok(operationLogService.selectListByPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords(), pageVo.getStart(), pageVo.getEnd()));
    }

    @OperationLogger("删除一条操作日志")
    @RequiresRoles(value = "root")
    @DeleteMapping("/del")
    public Result delOperationLog(@RequestParam("id") Long id)
    {
        operationLogService.delete(id);
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }

    @OperationLogger("删除全部操作日志")
    @RequiresRoles(value = "root")
    @DeleteMapping("/del/all")
    public Result delAllOperationLog()
    {
        operationLogService.deleteAll();
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }

    @OperationLogger("批量删除操作日志")
    @RequiresRoles(value = "root")
    @DeleteMapping("/del/part")
    public Result delPartOperationLog(@RequestParam("ids") List<Long> ids)
    {
        operationLogService.batchDelete();
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }
}
