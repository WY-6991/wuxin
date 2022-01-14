package com.wuxin.blog.controller.front.chatUrl;


import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.service.ChatUrlService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/chat/url")
public class ChatUrlController {

    @Resource
    private ChatUrlService chatUrlService;


    @OperationLogger("获取用户额外信息")
    @GetMapping("/find")
    public Result findChatUrlByUserId(@RequestParam(value = "userId") Long userId){
        return Result.ok(chatUrlService.findChatUrlByUserId(userId));
    }
}
