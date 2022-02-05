package com.wuxin.blog.controller.admin.friend;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Friend;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.FriendService;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:23
 * @Description:
 */
@RestController
@RequestMapping("/admin/friend")
public class AdminFriendController {

    @Resource
    private FriendService friendService;


    @OperationLogger("添加友情链接")
    @RequiresRoles("root")
    @PostMapping("/add")
    public Result addFriend(@RequestBody Friend friend){
        friendService.add(friend);
        return Result.ok("添加成功!");
    }

    @OperationLogger("修改友情链接")
    @RequiresRoles("root")
    @PostMapping("/update")
    public Result updateFriend(@RequestBody Friend friend){
        friendService.update(friend);
        return Result.ok("修改成功!");
    }

    @OperationLogger("删除友情链接")
    @RequiresRoles("root")
    @GetMapping("/del")
    public Result delFriend(@RequestParam("friendId") Long friendId){
        friendService.delete(friendId);
        return Result.ok("删除成功!");
    }

    @OperationLogger("查看友情链接列表")
    @PostMapping("/list")
    public Result findFriend(@RequestBody PageVo pageVo){
        return Result.ok(friendService.selectListByPage(pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords(),pageVo.getStart(),pageVo.getEnd()));
    }
}
