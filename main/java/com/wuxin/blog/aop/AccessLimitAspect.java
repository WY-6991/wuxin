package com.wuxin.blog.aop;

import com.wuxin.blog.annotation.AccessLimit;
import com.wuxin.blog.exception.ServiceException;
import com.wuxin.blog.interceptor.AccessLimitInterceptor;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.utils.ip.IpUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author: wuxin001
 * @Date: 2022/01/23/10:43
 * @Description:
 */
@Aspect
@Component
public class AccessLimitAspect {

    private final static Logger logger = LoggerFactory.getLogger(AccessLimitInterceptor.class);

    @Autowired
    private RedisService redisService;

    @Before("@annotation(accessLimit)")
    public void accessLimitImpl(JoinPoint point, AccessLimit accessLimit) throws Throwable {
        logger.info("==========================限流中=======================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        String method = targetMethod.getName().toLowerCase();

        int seconds = accessLimit.seconds();
        int limitCount = accessLimit.limitCount();
        // 获取操作访问ip 根据操作访问ip和访问路径设置key
        String ip = IpUtils.getIpAddr(request);
        String url = request.getRequestURI();

        String redisKey = url + ":" + method + ":" + ip;
        Integer count = (Integer) redisService.get(redisKey);
        if (StringUtils.isNull(count)) {
            redisService.incr(redisKey, 1);
            redisService.expire(redisKey, seconds);
            logger.info(" 对当前ip进行限流中:{} ip限流次数：{},限流最大次数：{}", ip, count, limitCount);
        } else {
            //超出规定限定次数 限制该ip继续访问
            if (count >= limitCount) {
                // 超过规定次数限制 提示限流操作
                throw new ServiceException(accessLimit.msg());
            } else {
                //没超出访问限制次数
                redisService.incr(redisKey, 1);
            }
        }


    }
}
