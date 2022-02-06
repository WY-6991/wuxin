package com.wuxin.blog.controller.front;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.service.MomentService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:24
 * @Description:
 */
@RequestMapping("/moment")
@RestController
public class MomentController {

    @Resource
    private MomentService momentService;

    @VisitLogger(value = "获取动态信息",name = "动态页")
    @GetMapping("/list/{current}/{limit}")
    public Result getMomentList(@PathVariable("current") int current,
                             @PathVariable("limit") int limit){
        return Result.ok(momentService.selectListByPage(current,limit));
    }
}
