package com.wuxin.blog.controller;

import com.wuxin.blog.service.MomentService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:24
 * @Description:
 */
@RequestMapping("/moment")
@RestController
public class MomentController {

    @Autowired
    private MomentService momentService;

    @GetMapping("/list")
    public Result findMoment(@RequestParam("current") int current,
                             @RequestParam("limit") int limit){
        return R.ok(momentService.findMoment(current,limit));
    }
}
