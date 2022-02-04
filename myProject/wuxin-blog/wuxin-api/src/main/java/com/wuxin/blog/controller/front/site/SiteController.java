package com.wuxin.blog.controller.front.site;

import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.mode.Site;
import com.wuxin.blog.pojo.system.MySystem;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.CategoryService;
import com.wuxin.blog.service.MySystemService;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/01/18/10:27
 * @Description:
 */
@RestController
@RequestMapping("/web/site")
public class SiteController {


    @Autowired
    private MySystemService systemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;



    @VisitLogger(value = "访问了站点内容",name = "首页")
    @GetMapping("/info")
    public Result getSiteInfo(){
        Result result = Result.ok();
        result.put("footerLabelList",systemService.findWebFooterLabel());
        result.put("categoryList",categoryService.list());
        result.put("tagList",tagService.list());
        result.put("newBlogList",blogService.newBlog());
        MySystem mySystem = systemService.findMySystem(MySystem.SYSTEM_ID);
        // 过滤不必要的信息
        result.put("site", Site.getSite(systemService.findMySystem(MySystem.SYSTEM_ID)));
        return result;
    }
}
