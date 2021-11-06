package com.wuxin.blog.controller.admin.friend;

import com.wuxin.blog.pojo.Friend;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.FriendService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:23
 * @Description:
 */
@RestController
@RequestMapping("/admin/friend")
public class AdminFriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/add")
    public Result addFriend(@RequestBody Friend friend){
        friendService.addFriend(friend);
        return R.ok("添加成功!");
    }


    @PostMapping("/update")
    public Result updateFriend(@RequestBody Friend friend){
        friendService.updateFriend(friend);
        return R.ok("修改成功!");
    }


    @GetMapping("/del")
    public Result delFriend(@RequestParam("friendId") int friendId){
        friendService.delFriend(friendId);
        return R.ok("删除成功!");
    }


    @PostMapping("/list")
    public Result findFriend(@RequestBody PageVo pageVo){
        return R.ok(friendService.findFriend(pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords()));
    }
}
