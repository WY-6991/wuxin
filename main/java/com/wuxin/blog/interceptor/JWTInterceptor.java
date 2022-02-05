package com.wuxin.blog.interceptor;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.wuxin.blog.constant.Constants;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.utils.security.JWTUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wuxin001
 * @Date: 2022/01/28/21:28
 * @Description: jwt保护接口拦截处理
 */
public class JWTInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JWTInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("jwt interception request:{}", JSONUtil.toJsonStr(request));
        // // 获取请求路径
        // StringBuffer requestUrl = request.getRequestURL();
        // if (!requestUrl.toString().contains("/admin")) {
        //     System.out.println("访问接口不需要认证");
        //     return true;
        // }
        // System.out.println("访问接口需要认证");
        // // 获取请求头中令牌
        // String token = request.getHeader(Constants.AUTHENTICATION);
        // if (StringUtils.isNull(token)) {
        //     // 如果请求头中获取不到尝试从cookie中获取request.getCookies()
        //     Cookie[] cookies = request.getCookies();
        //     if(cookies.length!=0){
        //         System.out.println("cookie"+cookies);
        //     }
        //     throw new JWTCreationException("获取不到令牌！", null);
        // }
        //
        //
        //
        // // jwt校验
        // try {
        //     JWTUtils.validToken(token);
        //     return true;
        // } catch (JWTCreationException e) {
        //     throw new JWTCreationException("签名错误！", e);
        // } catch (TokenExpiredException e) {
        //     throw new TokenExpiredException("令牌已过期");
        // } catch (AlgorithmMismatchException e) {
        //     throw new AlgorithmMismatchException("签名算法错误");
        //
        // } catch (Exception e) {
        //     throw new JWTCreationException("认证失败", e);
        // }
        return true;
    }
}
