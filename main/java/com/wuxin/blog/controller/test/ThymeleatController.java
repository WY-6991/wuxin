package com.wuxin.blog.controller.test;

import com.wuxin.blog.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2022/01/22/10:26
 * @Description:
 */
@Controller
public class ThymeleatController {



    @GetMapping("/test/mine")
    public String test1(Model mode){
        mode.addAttribute("date", "2022年1月22日");
        mode.addAttribute("title", "hello 树先生");
        mode.addAttribute("url", "https://www.baidu.com");
        mode.addAttribute("adminUrl", "https://www.baidu.com");
        mode.addAttribute("username", "wuxin001root");
        mode.addAttribute("content", "你是天边最美的云彩");
        return "mine";
    }
}
