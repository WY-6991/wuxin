package com.wuxin.blog.utils.logUtil;

import com.wuxin.blog.mode.Log;
import com.wuxin.blog.utils.ip.AddressUtils;
import com.wuxin.blog.utils.ip.IpUtils;
import com.wuxin.blog.utils.security.ShiroUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author: wuxin001
 * @Date: 2022/02/05/20:05
 * @Description:
 */
public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void setLogInfo(Log log, ProceedingJoinPoint joinPoint) {

        logger.info("========================日志捕获中=============================");
        //原始的HTTP请求和响应的信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        // 获取一些基本信息 地址 浏览器 型号 userAgent 操作系统等等
        String controller = targetMethod.getDeclaringClass().getTypeName();
        String method = targetMethod.getName().toLowerCase();
        String type = request.getMethod().toLowerCase();
        log.setMethod(controller + "." + method + "()");
        log.setType(type);
        String ipAddress = IpUtils.getIpAddr(request);
        // ip地址
        log.setIp(ipAddress);
        // ip来源
        log.setIpSource(AddressUtils.getRealAddressByIP(ipAddress));
        // 获取请求头
        String header = request.getHeader("User-Agent");
        // 解析请求头
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        // 获取操作系统
        log.setOs(userAgent.getOperatingSystem().toString().toLowerCase());
        // 获取浏览器型号
        log.setBrowser(userAgent.getBrowser().toString().toLowerCase());

    }

}
