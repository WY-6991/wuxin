package com.wuxin.blog.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/16:51
 * @Description: 执行 accesslog
 */
@Component
@Aspect
public class AccessLogAop {

    private static final Logger logger = LoggerFactory.getLogger(AccessLogAop.class);

    /**
     * 日志切面
     */
    // @Pointcut("execution(* com.wuxin.blog.controller.front.*.*.*(..))")
    // public void log() {
    //
    // }

    // @Around("log()")
    // public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    //     System.out.println("执行环绕通知日志");
    //     return joinPoint.proceed();
    // }

    // @Before("log()")
    // public void logBefore(JoinPoint joinPoint){
    //     logger.error("........................日志执行之前..before.....................");
    // }


    // @AfterReturning(value = "log()",returning = "object")
    // public Object logAfterReturn(Object object){
    //     logger.error("............................日志结束之后.....after return........................,{}",object);
    //     return object;
    // }

    // @After("log()")
    // public void logAfter(){
    //     System.out.println("............................日志结束之后...after..........................");
    // }


}
