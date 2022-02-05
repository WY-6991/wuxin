package com.wuxin.blog.aop;

import cn.hutool.json.JSONUtil;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.mode.Log;
import com.wuxin.blog.pojo.log.ExceptionLog;
import com.wuxin.blog.service.ExceptionService;
import com.wuxin.blog.utils.AopUtils;
import com.wuxin.blog.utils.ExceptionLogUtil;
import com.wuxin.blog.utils.ip.AddressUtils;
import com.wuxin.blog.utils.ip.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
            ExceptionLog exceptionLog = new ExceptionLog();
            // 处理公共部分信息
            getLogInfo(exceptionLog, (ProceedingJoinPoint) joinPoint);
            // 处理参数
            handleExceptionParams(joinPoint,exceptionLog,e);
            // 添加
            exceptionLogService.add(exceptionLog);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug("异常日志 =====>处理日志异常");
        }
    }

    public void getLogInfo(Log log, ProceedingJoinPoint joinPoint) throws Throwable {

        logger.error("========================异常日志捕获中=============================");
        //原始的HTTP请求和响应的信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 设置方法类型
        Method targetMethod = methodSignature.getMethod();
        // 获取一些基本信息 地址 浏览器 型号 userAgent 操作系统等等
        String controller = targetMethod.getDeclaringClass().getTypeName();
        String url = request.getRequestURI();
        String method = targetMethod.getName().toLowerCase();
        String type = request.getMethod().toLowerCase();
        String description = getDescriptionFromAnnotations(joinPoint);
        log.setUrl(url);
        log.setMethod(controller +"."+ method + "()");
        log.setType(type);
        String ipAddress = IpUtils.getIpAddr(request);
        // 添加访问标识
        log.setByCreate(request.getRequestedSessionId());


        // ip地址
        log.setIp(ipAddress);
        // 操作描述
        log.setDescription(description);
        // ip来源
        log.setIpSource(AddressUtils.getRealAddressByIP(ipAddress));
        // 获取请求头
        String header = request.getHeader("User-Agent");
        // 获取请求表示
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        // 获取操作系统
        log.setOs(userAgent.getOperatingSystem().toString().toLowerCase());
        // 获取浏览器型号
        log.setBrowser(userAgent.getBrowser().toString().toLowerCase());

    }


    /**
     * 处理异常日志参数
     * @param joinPoint 切面
     * @param exceptionLog result
     */
    public void handleExceptionParams(JoinPoint joinPoint,ExceptionLog exceptionLog,Exception e){
        String error = ExceptionLogUtil.getStackTrace(e);
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        exceptionLog.setParams(ExceptionLogUtil.substring(JSONUtil.toJsonStr(requestParams), 0, 2000));
        exceptionLog.setResult(ExceptionLogUtil.substring(error, 0, 2000));

    }

    /**
     * 获取注解信息
     * return 注解信息
     */
    private String getDescriptionFromAnnotations(JoinPoint joinPoint) {
        String description = "";
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
