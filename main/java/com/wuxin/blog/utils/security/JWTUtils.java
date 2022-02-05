package com.wuxin.blog.utils.security;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Map;

/**
 * 使用hutool jwtutil生成jwttoken
 *
 * @Author: wuxin001
 * @Date: 2022/01/28/21:11
 * @Description:
 */
@Component
public class JWTUtils {


    @Value("${token.header}")
    private static String header;
    @Value("${token.secret}")
    private static String secret;
    @Value("${token.expireTime}")
    private static Long expireTime;
    private static final String USERNAME = "username";
    private static final String ROLE_NAME = "roleName";


    /**
     * 生成token
     */
    public static String createToken(Map<String, String> map) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        // 创建jwt
        JWTCreator.Builder builder = JWT.create();
        builder.withExpiresAt(calendar.getTime());
        // builder.withHeader()
        map.forEach((K, V) -> {
            builder.withClaim(K, V);
        });
        String token = builder.sign(Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)));
        return token;

    }


    /**
     * 校验token
     */
    public static void validToken(String token) {
        JWT.require(Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8))).build().verify(token);
    }


    /**
     * 解析token
     */
    public static DecodedJWT parse(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8))).build().verify(token);
        String header = verify.getHeader();
        String payload = verify.getPayload();
        String signature = verify.getSignature();
        return null;
    }




}
