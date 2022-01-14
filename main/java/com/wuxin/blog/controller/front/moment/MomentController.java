package com.wuxin.blog.controller.front.moment;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.service.MomentService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @OperationLogger("获取动态信息")
    @GetMapping("/list")
    public Result findMoment(@RequestParam("current") int current,
                             @RequestParam("limit") int limit){
        return Result.ok(momentService.selectListByPage(current,limit));
    }
}
