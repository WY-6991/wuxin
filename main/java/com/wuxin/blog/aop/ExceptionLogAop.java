package com.wuxin.blog.aop;

import cn.hutool.json.JSONUtil;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.pojo.log.ExceptionLog;
import com.wuxin.blog.service.ExceptionService;
import com.wuxin.blog.utils.AopUtils;
import com.wuxin.blog.utils.ExceptionLogUtil;
import com.wuxin.blog.utils.logUtil.LogUtil;
import com.wuxin.blog.utils.security.ShiroUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2022/01/23/10:46
 * @Description: 异常日志捕获处理
 */
@Aspect
@Component
public class ExceptionLogAop {

    @Autowired
    ExceptionService exceptionLogService;


    private static final Logger logger = LoggerFactory.getLogger(ExceptionLogAop.class);

    /**
     * 捕获异常信息
     */
    @AfterThrowing(pointcut = "execution(* com.wuxin.blog.controller..*.*.*(..))", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e) throws Throwable {
        try {
            logger.debug("=========================异常日志捕获中========================");
            ExceptionLog exceptionLog = new ExceptionLog();
            // 处理公共部分信息
            LogUtil.setLogInfo(exceptionLog, (ProceedingJoinPoint) joinPoint);
            // 添加标识
            exceptionLog.setByCreate(ShiroUtil.createSalt(exceptionLog.getMethod()));
            // 获取注解描述信息
            String description = getDescriptionFromAnnotations(joinPoint);
            exceptionLog.setDescription(description);
            // 处理参数
            handleExceptionParams(joinPoint, exceptionLog, e);
            // 添加异常日志
            exceptionLogService.add(exceptionLog);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug("异常日志捕获失败！");
        }
    }



    /**
     * 处理异常日志参数
     */
    public void handleExceptionParams(JoinPoint joinPoint, ExceptionLog exceptionLog, Exception e) {
        String error = ExceptionLogUtil.getStackTrace(e);
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        exceptionLog.setParams(ExceptionLogUtil.substring(JSONUtil.toJsonStr(requestParams), 0, 2000));
        exceptionLog.setResult(ExceptionLogUtil.substring(error, 0, 2000));


    }


    /**
     * 获取注解信息
     */
    private static String getDescriptionFromAnnotations(JoinPoint joinPoint) {
        String description = null;
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        OperationLogger operationLogger = method.getAnnotation(OperationLogger.class);
        if (operationLogger != null) {
            description = operationLogger.value();
            return description;
        }
        VisitLogger visitLogger = method.getAnnotation(VisitLogger.class);
        if (visitLogger != null) {
            description = visitLogger.value();
            return description;
        }
        return description;

    }


}
