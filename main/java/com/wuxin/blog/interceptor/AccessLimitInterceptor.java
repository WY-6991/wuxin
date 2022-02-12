package com.wuxin.blog.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.wuxin.blog.annotation.AccessLimit;
import com.wuxin.blog.exception.ServiceException;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.utils.ip.IpUtils;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.servlet.ServletUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wuxin001
 * @Date: 2022/01/23/12:58
 * @Description: 访问注解拦截处理
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {


    private final static Logger logger = LoggerFactory.getLogger(AccessLimitInterceptor.class);

    @Autowired
    private RedisService redisService;

    /**
     * 限流拦截处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
            // 对于没有限流注解请求方法不需要限流拦截
            if (StringUtils.isNull(accessLimit)) {
                return true;
            }

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
                    return true;
                }
            }
        }
        return true;
    }
}
