package com.wuxin.blog.handle;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.wuxin.blog.constant.HttpStatus;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.exception.NotFoundException;
import com.wuxin.blog.exception.ServiceException;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.security.JWTUtils;
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
 * @Date: 2022/01/03/19:53
 * @Description: 全局异常处理器
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
        log.error("===============没有权限执行该操作===============");
        return Result.create(HttpStatus.FORBIDDEN, "没有权限执行该操作！");
    }

    /**
     * 没有权限执行操作
     *
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
    public Result usernameAndPasswodException() {
        log.error("================用户名或密码错误================");
        return Result.create(HttpStatus.CUSTOM, "用户名或密码错误！");
    }


    /**
     * 内容不存在
     *
     * @return error message
     */
    @ExceptionHandler(NotFoundException.class)
    public Result notFoundException() {
        log.error("==================请求资源不存在=============");
        return Result.create(HttpStatus.NOT_FOUND, "请求资源不存在~");
    }

    @ExceptionHandler(TokenExpiredException.class)
    public Result tokenExpiredException() {
        log.error("==================token过期=============");
        return Result.create(HttpStatus.UNAUTHORIZED, "令牌已过期，请重新登录~");
    }

    @ExceptionHandler(AlgorithmMismatchException.class)
    public Result algorithmMismatchException() {
        log.error("==================签名算法错误=============");
        return Result.create(HttpStatus.UNAUTHORIZED, "请重新登录~");
    }

    @ExceptionHandler(JWTCreationException.class)
    public Result jwtCreationException() {
        log.error("==================jwt异常错误=============");
        return Result.create(HttpStatus.UNAUTHORIZED, "请重新登录~");
    }

    /**
     * 空指针异常
     *
     * @return error message
     */
    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException() {
        log.error("=====================空指针异常====================");
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
        log.error("自定义异常信息 Request URL : {}, Exception:{} ", request.getRequestURI(), e);
        return Result.create(HttpStatus.CUSTOM, e.getMessage());
    }


    /**
     * 服务层异常
     * @param request 请求
     * @param e       异常
     * @return error message
     */
    @ExceptionHandler(ServiceException.class)
    public Result serviceException(HttpServletRequest request, ServiceException e) {
        log.error("ServiceException e:{} ", request.getRequestURI(), e);
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
     *
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
     *
     * @return error message
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedException() {
        log.error("==================请求方法不支持==================");
        return Result.create(HttpStatus.ERROR, "请求方法错误~");

    }

    /**
     * 其他所有异常在这里捕获
     *
     * @param request 请求
     * @param e       异常信息
     * @return error message
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("异常信息 Request URL : {}, Exception:{} ", request.getRequestURI(), e.getMessage());
        return Result.create(HttpStatus.ERROR, "抱歉,服务器开小差去了~");
    }

}
