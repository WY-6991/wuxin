package com.wuxin.blog.controller.front.update;


import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.service.UpdateQuestionService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 更新设置
 * @author Administrator
 */
@RequestMapping("/question")
@RestController
public class UpdateQuestionController {

    @Resource
    private UpdateQuestionService updateQuestionService;


    @OperationLogger("获取网站更新内容")
    @GetMapping("/list")
    public Result findAllQuestion(){
        return Result.ok(updateQuestionService.list());
    }
}
