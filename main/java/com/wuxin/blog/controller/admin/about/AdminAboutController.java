package com.wuxin.blog.controller.admin.about;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.About;
import com.wuxin.blog.service.AboutService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:23
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/admin/about")
public class AdminAboutController {

    @Resource
    private AboutService aboutService;

    @OperationLogger("修改关于我的页面内容")
    @RequiresRoles("root")
    @PutMapping("/update")
    public Result updateAbout(@RequestBody About about) {
        aboutService.update(about);
        return Result.ok("修改成功！");
    }


}
