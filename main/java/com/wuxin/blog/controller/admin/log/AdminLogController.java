package com.wuxin.blog.controller.admin.log;


import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.ExceptionService;
import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/log")
public class AdminLogController {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private ExceptionService exceptionService;



    @PostMapping("/login/list")
    public Result loginLogList(@RequestBody PageVo pageVo){
        return R.ok(loginLogService.findLoginList(pageVo.getCurrent(), pageVo.getLimit()));
    }

    @GetMapping("/login/del")
    public Result delLoginLog(@RequestParam("id") Long id){
        return R.ok(loginLogService.delLoginLog(id));
    }

    @PostMapping("/exception/list")
    public Result exceptionLogList(@RequestBody PageVo pageVo){
        return R.ok(exceptionService.findExceptionLog(pageVo.getCurrent(), pageVo.getLimit()));
    }

    @GetMapping("/exception/del")
    public Result exceptionLogDel(@RequestParam("id") Long id){
        return R.ok(exceptionService.delException(id));
    }

}
