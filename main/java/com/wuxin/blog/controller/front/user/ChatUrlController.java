package com.wuxin.blog.controller.front.user;


import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.service.ChatUrlService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/chat/url")
public class ChatUrlController {

    @Resource
    private ChatUrlService chatUrlService;


    @GetMapping("/find")
    public Result findChatUrlByUserId(@RequestParam(value = "userId") Long userId){
        return Result.ok(chatUrlService.findChatUrlByUserId(userId));
    }
}
