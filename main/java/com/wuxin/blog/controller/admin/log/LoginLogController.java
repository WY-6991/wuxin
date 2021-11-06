package com.wuxin.blog.controller.admin.log;


import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/admin/login/log")
@RestController
public class LoginLogController {


    @Autowired
    private LoginLogService loginLogService;


    @PostMapping("/list")
    public Result findLoginLogList(@RequestBody PageVo pageVo){
        return R.ok(loginLogService.findLoginList(pageVo.getCurrent(),pageVo.getLimit()));
    }
}
