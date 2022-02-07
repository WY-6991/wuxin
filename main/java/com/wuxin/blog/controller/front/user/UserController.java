package com.wuxin.blog.controller.front.user;


import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.mode.Blogger;
import com.wuxin.blog.pojo.blog.ChatUrl;
import com.wuxin.blog.pojo.blog.Hobby;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.service.ChatUrlService;
import com.wuxin.blog.service.HobbyService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/***
 * @Description: 用户
 * @Author: wuxin001
 * @Date: 2021/8/23 0023
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private UserService userService;


    @Resource
    private ChatUrlService chatUrlService;


    @Resource
    private HobbyService hobbyService;


    @GetMapping("/blogger/info")
    public Result findAdminUserInfo() {
        Result result = Result.ok();
        result.put("chatUrl",chatUrlService.findChatUrlByUserId(GlobalConstant.ADMIN_USER_ID));
        result.put("hobby",hobbyService.selectListByUserId(GlobalConstant.ADMIN_USER_ID));
        result.put("info", Blogger.getBlogger(userService.findAdminUserInfo(GlobalConstant.ADMIN_USER_ID)));
        return result;
    }
}
