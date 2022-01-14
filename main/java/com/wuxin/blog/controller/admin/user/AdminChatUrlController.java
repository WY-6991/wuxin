package com.wuxin.blog.controller.admin.user;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.ChatUrl;
import com.wuxin.blog.service.ChatUrlService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/admin/chat/url")
public class AdminChatUrlController {

    @Resource
    private ChatUrlService chatUrlService;

    @OperationLogger("修改用户其他信息")
    @PostMapping("/update")
    public Result updateChatUrl(@RequestBody ChatUrl chatUrl){
        chatUrlService.updateChatUrl(chatUrl);
        return Result.ok("拓展信息修改成功！");
    }

    @OperationLogger("查看用户其他信息")
    @GetMapping("/find")
    public Result findChatUrl(@RequestParam(value = "userId",required = false) Long userId){
        return Result.ok(chatUrlService.findChatUrlByUserId(userId));
    }
}
