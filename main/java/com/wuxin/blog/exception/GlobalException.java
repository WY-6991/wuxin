package com.wuxin.blog.exception;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.sun.javafx.runtime.eula.Eula;
import com.wuxin.blog.pojo.ExceptionLog;
import com.wuxin.blog.service.ExceptionService;
import com.wuxin.blog.util.ExceptionLogUtil;
import com.wuxin.blog.util.LogUtil;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * 全局异常配置
 */

@Slf4j
@ControllerAdvice
@RestController
public class GlobalException {

    @Autowired
    private ExceptionService exceptionService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 捕获自定义的404异常
     * @param request 请求
     * @param e       自定义抛出的异常信息
     * @return error message
     */
    @ExceptionHandler(NotFoundException.class)
    public Result notFoundExceptionHandler(HttpServletRequest request, NotFoundException e) {
        log.error("异常信息 Request URL : {}, Exception:{} ",request.getRequestURL(),e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * 捕获自定义的持久化异常
     *
     * @param request 请求
     * @param e       自定义抛出的异常信息
     * @return error message
     */
    @ExceptionHandler(PersistenceException.class)
    public Result persistenceExceptionHandler(HttpServletRequest request, PersistenceException e) {
        log.error("异常信息 Request URL : {}, Exception :{} :", request.getRequestURL(), e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * 捕获自定义的登录失败异常
     *
     * @param request 请求
     * @param e       自定义抛出的异常信息
     * @return
     */
    // @ExceptionHandler(UsernameNotFoundException.class)
    // public Result usernameNotFoundExceptionHandler(HttpServletRequest request, UsernameNotFoundException e) {
    //     log.error("Request URL : {}, Exception={} :", request.getRequestURL(), e);
    //     return R.create(401, "用户名或密码错误！");
    // }

    /**
     * 捕获其它异常
     *
     * @param request 请求
     * @param e       异常信息
     * @return error message
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("异常信息 Request URL : {}, Exception:{} ", request.getRequestURI(), e.getMessage());
        log.error("异常信息1 getCause:{} ", e.getCause());
        log.error("异常信息2 Exception:{} ", e);
        ExceptionLog exceptionLog = new ExceptionLogUtil().addException(request, e.getMessage(), new ExceptionLog());
        exceptionService.addException(exceptionLog);
        return R.create(500, "异常错误");
    }
}
