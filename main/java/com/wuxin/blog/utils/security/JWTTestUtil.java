package com.wuxin.blog.utils.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: wuxin001
 * @Date: 2022/02/04/13:40
 * @Description:
 */

@Component
public class JWTTestUtil {

    @Value("${token.header}")
    private static String header;
    @Value("${token.secret}")
    private static String secret;
    @Value("${token.expireTime}")
    private static Long expireTime;
    private static final String USERNAME = "username";
    private static final String ROLE_NAME = "roleName";

    // 生成token
    public void createToken(){
        byte[] key = secret.getBytes();
        JWT jwt = JWT.create()
                .setPayload(USERNAME, "1234567890")
                .setPayload(ROLE_NAME, true)
                .setExpiresAt(DateUtil.parse("2022-01-01"))
                .setKey(key);

        String token = jwt.sign();
    }



    // 解析token
    public void parseToken(String token){
        final JWT jwt = JWT.of(token);

        boolean verify = jwt.setKey(secret.getBytes()).verify();

        //
        // //header
        // Assert.assertEquals("JWT", jwt.getHeader(JWTHeader.TYPE));
        // Assert.assertEquals("HS256", jwt.getHeader(JWTHeader.ALGORITHM));
        // Assert.assertNull(jwt.getHeader(JWTHeader.CONTENT_TYPE));
        //
        // //payload
        // Assert.assertEquals("1234567890", jwt.getPayload("sub"));
        // Assert.assertEquals("looly", jwt.getPayload("name"));
        // Assert.assertEquals(true, jwt.getPayload("admin"));
    }


    // 校验token
    public void validateToken(String token){
        final boolean verify = JWT.of(token).setKey(StrUtil.utf8Bytes("123456")).verify();
        // Assert.assertTrue(verify);
    }

}
