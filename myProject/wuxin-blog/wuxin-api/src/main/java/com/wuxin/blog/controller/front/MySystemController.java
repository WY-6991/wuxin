package com.wuxin.blog.controller.front;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.system.MySystem;
import com.wuxin.blog.service.MySystemService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/12/24/23:06
 * @Description:
 */
@RestController
@RequestMapping("/system")
public class MySystemController {


    @Resource
    private MySystemService mySystemService;


    @GetMapping("/info")
    public Result findMySystem()
    {
        return Result.ok(mySystemService.findMySystem(MySystem.SYSTEM_ID));
    }


    @GetMapping("/find/footer/label")
    public Result findWebFooterLabel()
    {
        return Result.ok(mySystemService.findWebFooterLabel());
    }

}
