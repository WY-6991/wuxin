package com.wuxin.blog.controller.admin;

import com.wuxin.blog.pojo.Moment;
import com.wuxin.blog.pojo.User;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.MomentService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:24
 * @Description: 动态
 */
@Slf4j
@RestController
@RequestMapping("/admin/moment")
public class AdminMomentController {

    @Autowired
    private MomentService momentService;


    @PostMapping("/add")
    public Result addMoment(@RequestBody Moment moment){
        log.info("add moment :{}",moment);
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        if(principal==null) return R.error("未登录");
        moment.setUserId(principal.getUserId());
        momentService.addMoment(moment);
        return R.ok("添加成功!");
    }


    @PostMapping("/update")
    public Result updateMoment(@RequestBody Moment moment){
        momentService.updateMoment(moment);
        return R.ok("修改成功!");
    }


    @GetMapping("/del")
    public Result delMoment(@RequestParam("momentId") Long momentId){
        momentService.delMoment(momentId);
        return R.ok("删除成功!");
    }

    @GetMapping("/detail")
    public Result detailMoment(@RequestParam("momentId") Long momentId){
        return R.ok(momentService.momentDetail(momentId));
    }

    @PostMapping("/list")
    public Result findMoment(@RequestBody PageVo pageVo){
        return R.ok(momentService.findMoment(pageVo.getCurrent(), pageVo.getLimit()));
    }





}
