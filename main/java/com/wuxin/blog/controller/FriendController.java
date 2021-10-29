package com.wuxin.blog.controller;

import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.FriendService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:23
 * @Description: 友情链接
 */
@Slf4j
@RequestMapping("/friend")
@RestController
public class FriendController {


    @Autowired
    private FriendService friendService;


    @PostMapping("/list")
    public Result findFriend(@RequestBody PageVo pageVo){
        log.info("friend list:{}",pageVo);
        return R.ok(friendService.findFriend(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }




}

