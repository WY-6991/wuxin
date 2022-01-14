package com.wuxin.blog.handle;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.wuxin.blog.constant.HttpStatus;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wuxin001
 * @Date: 2022/01/03/16:53
 * @Description: 捕获异常并且抛出异常
 */
@RestControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);


    /**
     * 未登录
     *
     * @return message
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result unauthorizedException() {
        log.error("===============未登录===============");
        return Result.create(HttpStatus.FORBIDDEN, "没有权限执行该操作！");
    }

    /**
     * 没有权限执行操作
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Result unauthenticatedException() {
        log.error("================拒绝访问，请登录之后再试============");
        return Result.create(HttpStatus.UNAUTHORIZED, "拒绝访问，请登录之后再试！");
    }

    /**
     * 登录异常
     *
     * @return message
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result usernameNotFoundExceptionHandler() {
        log.error("用户名或密码错误");
        return Result.create(HttpStatus.ERROR, "用户名或密码错误！");
    }


    /**
     * 空指针异常
     *
     * @return error message
     */
    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException() {
        log.error("空指针异常");
        return Result.create(HttpStatus.ERROR, "空指针异常~");
    }



    /**
     * 自定义异常
     *
     * @param request 请求
     * @param e       异常
     * @return error message
     */
    @ExceptionHandler(CustomException.class)
    public Result myCustomException(HttpServletRequest request, CustomException e) {
        log.error("异常信息 Request URL : {}, Exception:{} ", request.getRequestURI(), e);
        return Result.create(HttpStatus.CUSTOM, e.getMessage());
    }


    /**
     * 用户未登录抛出异常
     *
     * @return error message
     */
    @ExceptionHandler(MybatisPlusException.class)
    public Result mybatisPlusException() {
        log.error("操作数据库失败");
        return Result.create(HttpStatus.ERROR, "操作数据库失败!");
    }


    /**
     * 访问路径不存在
     * @return error message
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public Result notFoundExceptionHandler() {
        log.error("访问路径不存在");
        return Result.create(HttpStatus.NOT_FOUND, "访问路径不存在~");

    }

    /**
     * 请求方法不支持
     * @return error message
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedException() {
        log.error("请求方法不支持");
        return Result.create(HttpStatus.ERROR, "请求方法不支持~");

    }

    /**
     * 服务器异常
     *
     * @param request 请求
     * @param e       异常信息
     * @return error message
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("异常信息 Request URL : {}, Exception:{} ", request.getRequestURI(), e);
        // if (e instanceof UnauthorizedException) {
        //     return Result.create(HttpStatus.CUSTOM, "没有权限执行该操作~");
        // }
        // if (e instanceof SQLException) {
        //     return Result.create(HttpStatus.CUSTOM, "sql语句异常~");
        // }
        // if (e instanceof MybatisPlusException) {
        //     return Result.create(HttpStatus.CUSTOM, "数据库操作失败~");
        // }
        // if (e instanceof NoHandlerFoundException) {
        //     return Result.create(HttpStatus.NOT_FOUND, "访问路径不存在~");
        // }
        return Result.create(HttpStatus.ERROR, "抱歉,服务器开小差去了~");
    }

}
