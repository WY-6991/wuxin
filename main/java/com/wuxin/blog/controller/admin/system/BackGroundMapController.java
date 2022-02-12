package com.wuxin.blog.controller.admin.system;

import com.wuxin.blog.annotation.AccessLimit;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.system.BackgroundMap;
import com.wuxin.blog.service.BackgroundMapService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/12/14/15:48
 * @Description: 背景图控制器
 */

@RestController
@RequestMapping("/background")
@Slf4j
public class BackGroundMapController {

    @Autowired
    private BackgroundMapService backgroundMapService;


    @AccessLimit(seconds = 60, limitCount = 10, msg = "操作频率过高！一分钟之后再试！")
    @OperationLogger("查看背景图")
    @GetMapping("/list")
    public Result findBackGroundMap(){

        return Result.ok(backgroundMapService.list());
    }
    @RequiresRoles("root")
    @OperationLogger("删除背景图")
    @DeleteMapping("/del")
    public Result delBackgroundMapById(@RequestParam("id") Long id){
        backgroundMapService.delete(id);
        return Result.ok("背景图删除成功！");
    }

    @RequiresRoles("root")
    @OperationLogger("修改背景图")
    @PostMapping("/update")
    public Result updateBackGroundMap(@RequestBody BackgroundMap backgroundMap){
        backgroundMapService.update(backgroundMap);
        return Result.ok("背景图修改成功！");
    }

    @RequiresRoles("root")
    @OperationLogger("添加背景图")
    @PostMapping("/add")
    public Result addBackGroundMap(@RequestBody BackgroundMap backgroundMap){
        backgroundMapService.add(backgroundMap);
        return Result.ok("背景图添加成功！");
    }





}
