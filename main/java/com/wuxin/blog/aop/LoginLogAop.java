package com.wuxin.blog.aop;


import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.pojo.LoginLog;
import com.wuxin.blog.util.IpUtil;
import com.wuxin.blog.util.ServletUtil;
import com.wuxin.blog.util.result.Result;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LoginLogAop {

    @Autowired
    private LoginLogService loginLogService;

    private LoginLog loginLog;


    /**
     * 指定切入登录方法
     */
    @Pointcut("execution(* com.wuxin.blog.controller.user.LoginController.userLogin(..))")
    public void log() {
        log.info("登录中....");
    }

    @Before("log()")
    public void doLoginBefore() {

        // 获取请求用户基本参数 用户sessionID，请求参数，
        loginLog = new LoginLog();
        HttpServletRequest request = ServletUtil.getRequest();
        // 获取一些基本信息 地址 浏览器 型号 userAgent 操作系统等等
        loginBeforeInfo(loginLog, request);


    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void loginAfterReturn(Result result) {
        // 根据返回结果获取登录状态
        loginLog.setStatus(result.getMessage());
        loginLog.setCode(result.getCode());
    }


    @After("log()")
    public void doLoginAfter() {
        loginLogService.addLoginLog(loginLog);
    }


    public void loginBeforeInfo(LoginLog loginLog, HttpServletRequest request) {

        String requestedSessionId = request.getRequestedSessionId();
        if(Objects.equals(requestedSessionId, "")||Objects.equals(requestedSessionId,null)){
            // 获取sessionID
            loginLog.setUsername("未知用户");
        }else {
            loginLog.setUsername(requestedSessionId);
        }

        //ip 号
        String ipAddress = IpUtil.getIpAddress(request);
        loginLog.setIp(ipAddress);
        // ip地址
        loginLog.setIpSource(IpUtil.getCityInfo(ipAddress));
        // 获取请求头
        String header = request.getHeader("User-Agent");
        // 获取请求表示
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        // 获取操作系统
        loginLog.setOs(userAgent.getOperatingSystem().toString());
        // 获取浏览器型号
        loginLog.setBrowser(userAgent.getBrowser().toString());
        // 获取请求user-agent
        loginLog.setUserAgent(header);
    }


}
