package com.wuxin.blog.controller.admin.user;


import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Hobby;
import com.wuxin.blog.service.HobbyService;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/admin/hobby")
public class AdminHobbyController {

    @Resource
    private HobbyService hobbyService;




    @OperationLogger("添加爱好")
    @PostMapping("/add")
    public Result addHobby(@RequestBody Hobby hobby){
        hobbyService.add(hobby);
        return Result.ok("添加成功");
    }

    @OperationLogger("修改爱好")
    @PostMapping("/update")
    public Result updateHobby(@RequestBody Hobby hobby){
        hobbyService.update(hobby);
        return Result.ok("添加成功");
    }


    @RequiresRoles("root")
    @OperationLogger("删除爱好")
    @GetMapping("/del")
    public Result delHobby(@RequestParam(value = "id",required = false) Long id){
        hobbyService.delete(id);
        return Result.ok("添加成功");
    }
    @OperationLogger("查看爱好")
    @GetMapping("/list")
    public Result findUserHobby(@RequestParam(value = "userId",required = false) Long userId){
        return Result.ok(hobbyService.selectListByUserId(userId));
    }
}
