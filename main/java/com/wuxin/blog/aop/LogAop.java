package com.wuxin.blog.aop;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.mode.LoginBody;
import com.wuxin.blog.mode.UserAccount;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.pojo.log.AccessLog;
import com.wuxin.blog.pojo.log.ExceptionLog;
import com.wuxin.blog.pojo.log.LoginLog;
import com.wuxin.blog.pojo.log.OperationLog;
import com.wuxin.blog.service.AccessLogService;
import com.wuxin.blog.service.ExceptionService;
import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.service.OperationLogService;
import com.wuxin.blog.utils.AopUtils;
import com.wuxin.blog.utils.ExceptionLogUtil;
import com.wuxin.blog.utils.ip.AddressUtils;
import com.wuxin.blog.utils.ip.IpUtils;
import com.wuxin.blog.mode.Log;
import com.wuxin.blog.utils.logUtil.LogUtil;
import com.wuxin.blog.utils.security.MySecurityUtils;
import com.wuxin.blog.utils.security.ShiroUtil;
import com.wuxin.blog.utils.string.StringUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: wuxin001
 * @Date: 2021/12/17/17:01
 * @Description:
 */
@Aspect
@Component
public class LogAop {

    // private static final Logger logger = LoggerFactory.getLogger(LogAop.class);
    //
    // @Autowired
    // private LoginLogService loginLogService;
    //
    // @Autowired
    // private ExceptionService exceptionLogService;
    //
    // @Autowired
    // private AccessLogService accessLogService;
    //
    // @Autowired
    // private OperationLogService operationLogService;
    //
    // /**
    //  * 登录日志aop
    //  */
    // @Around(" execution(* com.wuxin.blog.controller.front.user.LoginController.userLogin(..))")
    // public Object loginLogAopControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    //     try {
    //         logger.info("==========================登录日志捕获中=======================");
    //         //获取参数
    //         LoginLog loginLog = new LoginLog();
    //         Object[] args = proceedingJoinPoint.getArgs();
    //         LoginBody loginUser = (LoginBody) args[0];
    //         // 登录用户信息
    //         loginLog.setUsername(loginUser.getUsername());
    //         // 登录其他信息 比如地点 ip 地址 浏览器型号等信息
    //         LogUtil.setLogInfo(loginLog, proceedingJoinPoint);
    //         // 登录结果
    //         handleResult(loginLog, proceedingJoinPoint);
    //         // 生成访问标识 用户+ip+来源+浏览器型号+操作型号
    //         String userIdCard = ShiroUtil.createSalt(loginLog.getUsername() + loginLog.getBrowser() + loginLog.getOs() + loginLog.getIp() + loginLog.getIpSource());
    //         // 用户标识
    //         loginLog.setByCreate(userIdCard);
    //         // 登录日志结果存入数据库
    //         loginLogService.add(loginLog);
    //     } catch (Throwable e) {
    //         e.printStackTrace();
    //         logger.debug("登录日志捕获异常！ ");
    //     }
    //
    //
    //     logger.info("用户登录日志捕获中=>{}", proceedingJoinPoint.proceed());
    //     return proceedingJoinPoint.proceed();
    // }
    //
    // /**
    //  * 访问日志aop
    //  */
    // @Around("execution(* com.wuxin.blog.controller.front.*.*.*(..))")
    // public Object accessLogAopControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    //     // 获取请求用户基本参数 用户sessionID，请求参数，
    //     try {
    //         logger.info("===========================访问日志捕获中=======================");
    //         AccessLog accessLog = new AccessLog();
    //         // 获取访问日志注解信息
    //         accessLog.setDescription(getDescriptionFromAnnotations(proceedingJoinPoint));
    //         // 获取访问日志基本信息
    //         LogUtil.setLogInfo(accessLog, proceedingJoinPoint);
    //         // 添加访问日志标识 ip ip来源 os
    //         String byCreate = ShiroUtil.createSalt(accessLog.getIp() + accessLog.getIpSource() + accessLog.getOs());
    //         accessLog.setByCreate(byCreate);
    //         //处理访问结果
    //         handleResult(accessLog, proceedingJoinPoint);
    //         accessLogService.add(accessLog);
    //
    //     } catch (Throwable e) {
    //         e.printStackTrace();
    //         logger.debug("访问日志捕获失败！ ");
    //     }
    //     return proceedingJoinPoint.proceed();
    // }
    //
    //
    //
    //
    // /**
    //  * 操作日志aop
    //  */
    // @Around("execution(* com.wuxin.blog.controller.admin.*.*.*(..))")
    // public Object operationLogAopControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    //     // 获取请求用户基本参数 用户sessionID，请求参数，
    //     try {
    //         logger.info("======================操作日志捕获中========================");
    //
    //         // 操作日志
    //         OperationLog operationLog = new OperationLog();
    //         User user = MySecurityUtils.getUser();
    //         operationLog.setUsername(user.getNickname());
    //         // 获取注解信息
    //         operationLog.setDescription(getDescriptionFromAnnotations(proceedingJoinPoint));
    //         // 获取基本操作信息
    //         LogUtil.setLogInfo(operationLog, proceedingJoinPoint);
    //         // 操作日志标识 用户名 方法名 ip地址 ip来源 操作系统
    //         String operationIdCard = user.getNickname() + operationLog.getMethod() + operationLog.getIp() + operationLog.getIp() + operationLog.getOs();
    //         operationLog.setByCreate(ShiroUtil.createSalt(operationIdCard));
    //         // 获取请求结果
    //         handleResult(operationLog, proceedingJoinPoint);
    //         // 添加到数据库中
    //         operationLogService.add(operationLog);
    //
    //     } catch (Throwable e) {
    //         e.printStackTrace();
    //         logger.debug("操作日志捕获失败！ ");
    //     }
    //     return proceedingJoinPoint.proceed();
    // }
    //
    // /**
    //  * 获取操作注解信息
    //  */
    // private String getDescriptionFromAnnotations(JoinPoint joinPoint) {
    //     String description = null;
    //     Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    //     OperationLogger operationLogger = method.getAnnotation(OperationLogger.class);
    //     if (operationLogger != null) {
    //         description = operationLogger.value();
    //         return description;
    //     }
    //     VisitLogger visitLogger = method.getAnnotation(VisitLogger.class);
    //     if (visitLogger != null) {
    //         description = visitLogger.value();
    //         return description;
    //     }
    //     return description;
    //
    // }
    //
    // public void handleResult(Log log, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    //     try {
    //         JSONObject jsonObject = JSONUtil.parseObj(proceedingJoinPoint.proceed());
    //         Integer code = (Integer) jsonObject.get("code");
    //         String message = (String) jsonObject.get("message");
    //         log.setCode(code);
    //         log.setResult(message);
    //     } catch (Throwable e) {
    //         e.printStackTrace();
    //         logger.debug("日志结果捕获异常");
    //     }
    // }


}
