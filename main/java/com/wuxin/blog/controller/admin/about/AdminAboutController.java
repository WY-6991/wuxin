package com.wuxin.blog.controller.admin.about;

import com.wuxin.blog.pojo.About;
import com.wuxin.blog.service.AboutCommentService;
import com.wuxin.blog.service.AboutService;
import com.wuxin.blog.util.GlobalConstant;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import jdk.internal.util.xml.impl.ReaderUTF8;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:23
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/admin/about")
public class AdminAboutController {

    @Autowired
    private AboutService aboutService;


    @Autowired
    private AboutCommentService aboutCommentService;

    /**
     * 添加关于内容，这个方法应该是不会执行的，我的关于内容只有一页，所以不需要添加了
     * 直接写死
     *
     * @param about
     * @return
     */
    @PostMapping("/add")
    public Result addAbout(@RequestBody About about) {
        int i = aboutService.addAbout(about);
        if (i == 1) {
            return R.ok("添加成功！");
        }
        return R.error("添加失败！");

    }


    /**
     * 修改关于内容基本上是走的这个方法
     * 修改的id是一致的直接写死
     *
     * @param about
     * @return
     */
    @PostMapping("/update")
    public Result updateAbout(@RequestBody About about) {
        about.setAboutId(GlobalConstant.aboutId);
        log.info("update about ： {}",about);
        boolean b = aboutService.updateAbout(about);
        if (b) {
            return R.ok("修改成功！");
        }
        return R.error("修改失败！");

    }



    @GetMapping("/find")
    public Result findAbout() {
        return R.ok(aboutService.findAbout(GlobalConstant.aboutId));
    }




}
