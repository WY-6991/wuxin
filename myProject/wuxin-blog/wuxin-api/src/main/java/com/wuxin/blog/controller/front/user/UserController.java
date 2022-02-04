package com.wuxin.blog.controller.front.user;


import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.constant.Constants;
import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.exception.UserException;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.ChatUrlService;
import com.wuxin.blog.service.HobbyService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.string.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


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

    @Resource
    private RedisService redisService;


    @GetMapping("/blogger/info")
    public Result findAdminUserInfo(){
        String k = RedisKey.USER_INFO;
        String hk = RedisKey.BLOGGER_INFO;
        // 从redis中获取博主信息
        boolean b = redisService.hHasKey(k, hk);
        if(b){
            Map<String, Object> map = (Map<String, Object>) redisService.hget(k, hk);
            if(!map.isEmpty()){
                return Result.ok(map);
            }
        }
        Map<String, Object> hashMap = new HashMap<>(Constants.HASH_MAP_INIT);
        hashMap.put("chatUrl",chatUrlService.findChatUrlByUserId(GlobalConstant.ADMIN_USER_ID));
        hashMap.put("info",userService.findAdminUserInfo(GlobalConstant.ADMIN_USER_ID));
        hashMap.put("hobby",hobbyService.selectListByUserId(GlobalConstant.ADMIN_USER_ID));
        // 存入redis中
        redisService.hset(k,hk,hashMap);
        return Result.ok(hashMap);
    }
}
