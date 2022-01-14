package com.wuxin.blog.controller.admin.moment;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Moment;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.MomentService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:24
 * @Description: 动态
 */
@Slf4j
@RestController
@RequestMapping("/admin/moment")
public class AdminMomentController {

    @Resource
    private MomentService momentService;

    @OperationLogger("发布一条动态")
    @PostMapping("/add")
    public Result addMoment(@RequestBody Moment moment) {
        log.info("add moment :{}", moment);
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return Result.error("未登录");
        }
        moment.setUserId(principal.getUserId());
        momentService.add(moment);
        return Result.ok("添加成功!");
    }

    @OperationLogger("修改动态")
    @PostMapping("/update")
    public Result updateMoment(@RequestBody Moment moment) {
        momentService.update(moment);
        return Result.ok("修改成功!");
    }

    @OperationLogger("删除动态")
    @GetMapping("/del")
    public Result delMoment(@RequestParam("momentId") Long momentId) {
        momentService.delete(momentId);
        return Result.ok("删除成功!");
    }

    @OperationLogger("查看动态详情")
    @GetMapping("/detail")
    public Result detailMoment(@RequestParam("momentId") Long momentId) {
        return Result.ok(momentService.find(momentId));
    }

    @OperationLogger("查看动态列表")
    @PostMapping("/list")
    public Result findMoment(@RequestBody PageVo pageVo) {
        return Result.ok(
                momentService.selectListByPage
                        (pageVo.getCurrent(),
                                pageVo.getLimit(), pageVo.getKeywords(),pageVo.getStart(), pageVo.getEnd())
        );
    }


}
