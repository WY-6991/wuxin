package com.wuxin.blog.aop;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.exception.CustomException;
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
import com.wuxin.blog.utils.security.MySecurityUtils;
import com.wuxin.blog.utils.string.StringUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
import java.util.UUID;

/**
 * @Author: wuxin001
 * @Date: 2021/12/17/17:01
 * @Description:
 */
@Aspect
@Component
public class LogAop {
    private static final Logger log = LoggerFactory.getLogger(LogAop.class);

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private ExceptionService exceptionLogService;

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 登录日志aop
     *
     * @param proceedingJoinPoint
     * @return result
     * @throws Throwable 异常
     */
    @Around(" execution(* com.wuxin.blog.controller.front.user.LoginController.userLogin(..))")
    public Object loginLogAopControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取请求用户基本参数 用户sessionID，请求参数，
        try {
            //获取参数
            // LoginLog loginLog = new LoginLog();
            // Object[] objects = proceedingJoinPoint.getArgs();
            // log.info("登录用户参数信息:{}", JSONUtil.toJsonStr(objects));
            // UserAccount user = (UserAccount) objects[0];
            // loginLog.setUsername(user.getUsername());
            // loginLog.setParams(user.getUsername());
            // getLogInfo(loginLog, proceedingJoinPoint);
            // handleLoginParams(loginLog, proceedingJoinPoint);
            // // 登录用户信息设的密码设置为null;
            // loginLogService.add(loginLog);
            log.info("登录日志捕获中...");

        } catch (Throwable e) {
            e.printStackTrace();
            log.debug("登录日志处理异常 ");
        }

        return proceedingJoinPoint.proceed();
    }

    /**
     * 访问日志aop
     *
     */
    @Around("execution(* com.wuxin.blog.controller.front.*.*.*(..))")
    public Object accessLogAopControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取请求用户基本参数 用户sessionID，请求参数，
        try {
            // AccessLog accessLog = new AccessLog();
            // getLogInfo(accessLog, proceedingJoinPoint);
            // log.info("访问信息:{}", JSONUtil.toJsonStr(accessLog));
            // // 处理访问日志参数
            // handleLoginParams(accessLog,proceedingJoinPoint);
            // accessLogService.add(accessLog);
            log.info("访问日志捕获中...");
        } catch (Throwable e) {
            e.printStackTrace();
            log.debug("访问日志处理异常 ");
        }
        return proceedingJoinPoint.proceed();
    }


    /**
     * 操作日志aop
     */
    @Around("execution(* com.wuxin.blog.controller.admin.*.*.*(..))")
    public Object operationLogAopControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取请求用户基本参数 用户sessionID，请求参数，
        try {
            // 操作日志
            // OperationLog operationLog = new OperationLog();
            // getLogInfo(operationLog, proceedingJoinPoint);
            // // 获取用户名
            // User user = MySecurityUtils.getUser();
            // if (StringUtils.isNull(user)) {
            //     throw new CustomException("获取不到用户信息,请登录之后再试");
            // }
            // operationLog.setUsername(user.getNickname());
            // // 处理操作日志参数
            // handleLoginParams(operationLog,proceedingJoinPoint);
            // operationLogService.add(operationLog);
            log.info("操作日志捕获中....");

        } catch (Throwable e) {
            e.printStackTrace();
            log.debug("操作日志处理异常 ");
        }
        return proceedingJoinPoint.proceed();
    }


    /**
     * 捕获异常信息
     */
    // @AfterThrowing(pointcut = "execution(* com.wuxin.blog.controller..*.*.*(..))", throwing = "e")
    // public void logAfterThrowing(JoinPoint joinPoint, Exception e) throws Throwable {
    //     try {
    //         ExceptionLog exceptionLog = new ExceptionLog();
    //         // 处理公共部分信息
    //         getLogInfo(exceptionLog, (ProceedingJoinPoint) joinPoint);
    //         // 处理参数
    //         handleExceptionParams(joinPoint,exceptionLog,e);
    //         // 添加
    //         exceptionLogService.add(exceptionLog);
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //         log.debug("异常日志 =====>处理日志异常");
    //     }
    // }

    public void getLogInfo(Log log, ProceedingJoinPoint joinPoint) throws Throwable {

        LogAop.log.info("========================日志处理中=============================");
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


    // /**
    //  * 处理异常日志参数
    //  * @param joinPoint 切面
    //  * @param exceptionLog result
    //  */
    // public void handleExceptionParams(JoinPoint joinPoint,ExceptionLog exceptionLog,Exception e){
    //     String error = ExceptionLogUtil.getStackTrace(e);
    //     Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
    //     exceptionLog.setParams(ExceptionLogUtil.substring(JSONUtil.toJsonStr(requestParams), 0, 2000));
    //     exceptionLog.setResult(ExceptionLogUtil.substring(error, 0, 2000));
    //
    // }


    // 处理登录参数信息
    public void handleLoginParams(Log log, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        JSONObject jsonObject = JSONUtil.parseObj(proceedingJoinPoint.proceed());
        Integer code = (Integer) jsonObject.get("code");
        String message = (String) jsonObject.get("message");
        log.setCode(code);
        log.setResult(message);
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
    //
    // public String getUserUUID(String username,String password){
    //
    // }







}
