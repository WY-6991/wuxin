package com.wuxin.blog.interceptor;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.wuxin.blog.constant.Constants;
import com.wuxin.blog.utils.security.JWTUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wuxin001
 * @Date: 2022/01/28/21:28
 * @Description: jwt保护接口拦截处理
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {


    @Value("${product.dev}")
    private String env;

    private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(env.equals("product")){
            return true;
        }

        // token拦截器仅仅只对非admin路径操作
        if (!request.getRequestURL().toString().contains("/admin")) {
            log.info("====================访问接口无需认证=============");
            return true;
        }

        if("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;

        }



        response.setCharacterEncoding(Constants.UTF8);
        String token = request.getHeader(Constants.AUTHENTICATION);
        // request.getHeaders()
        log.info("jwt interception request:{},token:{}", JSONUtil.toJsonStr(request),token);
        //jwt校验
        try {
            JWTUtils.validToken(token);
            return true;
        } catch (JWTCreationException e) {
            throw new JWTCreationException("签名错误！", e);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("令牌已过期");
        } catch (AlgorithmMismatchException e) {
            throw new AlgorithmMismatchException("签名算法错误");

        } catch (Exception e) {
            throw new JWTCreationException("认证失败", e);
        }
    }
}
