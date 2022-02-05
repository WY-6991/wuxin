package com.wuxin.blog.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.wuxin.blog.annotation.RepeatSubmit;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.utils.ip.IpUtils;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.servlet.ServletUtils;
import com.wuxin.blog.utils.string.StringUtils;
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

    @Autowired
    private RedisService redisService;


    /**
     * 限流拦截处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("===============================限流处理拦截器===========================");
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RepeatSubmit accessLimit = method.getMethodAnnotation(RepeatSubmit.class);
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
            System.out.println("count" + count);
            if (StringUtils.isNull(count)) {
                //Todo redis序列化出现问题 获取失败
                redisService.incr(redisKey, 1);
                // 过期时间
                redisService.expire(redisKey, seconds);
            } else {
                //超出规定限定次数 限制该ip继续访问
                if (count >= limitCount) {
                    Result result = Result.error(accessLimit.msg());
                    ServletUtils.renderString(response, JSONObject.toJSONString(result));
                    return false;
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
