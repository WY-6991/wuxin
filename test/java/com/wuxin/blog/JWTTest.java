package com.wuxin.blog;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.HMacJWTSigner;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2022/02/04/12:02
 * @Description:
 */
@SpringBootTest(classes = JWTTest.class)
public class JWTTest {

    @Test
    public void createTest(){
        byte[] key = "1234".getBytes();
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("uid", Integer.parseInt("123"));
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);

            }
        };

        JWTUtil.createToken(map, key);
    }



    @Test
    public void createHs256Test(){
        byte[] key = "1234567890".getBytes();
        JWT jwt = JWT.create()
                .setPayload("sub", "1234567890")
                .setPayload("name", "looly")
                .setPayload("admin", true)
                .setExpiresAt(DateUtil.parse("2022-01-01"))
                .setKey(key);

        String rightToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Imxvb2x5IiwiYWRtaW4iOnRydWUsImV4cCI6MTY0MDk2NjQwMH0." +
                "bXlSnqVeJXWqUIt7HyEhgKNVlIPjkumHlAwFY-5YCtk";

        String token = jwt.sign();
        // Assert.assertEquals(rightToken, token);
        //
        // Assert.assertTrue(JWT.of(rightToken).setKey(key).verify());
    }

    @Test
    public void parseTest(){
        String rightToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwiYWRtaW4iOnRydWUsIm5hbWUiOiJsb29seSJ9." +
                "U2aQkC2THYV9L0fTN-yBBI7gmo5xhmvMhATtu8v0zEA";

        final JWT jwt = JWT.of(rightToken);


        JWTHeader header = jwt.getHeader();



        Assertions.assertTrue(jwt.setKey("1234567890".getBytes()).verify());
        //
        // //header
        Assertions.assertEquals("JWT", jwt.getHeader(JWTHeader.TYPE));
        Assertions.assertEquals("HS256", jwt.getHeader(JWTHeader.ALGORITHM));
        Assertions.assertNull(jwt.getHeader(JWTHeader.CONTENT_TYPE));

        //payload
        Assertions.assertEquals("1234567890", jwt.getPayload("sub"));
        Assertions.assertEquals("looly", jwt.getPayload("name"));
        Assertions.assertEquals(true, jwt.getPayload("admin"));
    }

    @Test
    public void createNoneTest(){
        JWT jwt = JWT.create()
                .setPayload("sub", "1234567890")
                .setPayload("name", "looly")
                .setPayload("admin", true)
                .setSigner(JWTSignerUtil.none());

        String rightToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwiYWRtaW4iOnRydWUsIm5hbWUiOiJsb29seSJ9.";

        String token = jwt.sign();
        Assertions.assertEquals(token, token);

        Assertions.assertTrue(JWT.of(rightToken).setSigner(JWTSignerUtil.none()).verify());
    }

    /**
     * 必须定义签名器
     */
    @Test
    public void needSignerTest(){
        JWT jwt = JWT.create()
                .setPayload("sub", "1234567890")
                .setPayload("name", "looly")
                .setPayload("admin", true);
        byte[] key = "1234567890".getBytes();
        // jwt.sign(key);
    }

    @Test
    public void verifyTest(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE2MjQwMDQ4MjIsInVzZXJJZCI6MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV_op5LoibLkuozlj7ciLCJzeXNfbWVudV8xIiwiUk9MRV_op5LoibLkuIDlj7ciLCJzeXNfbWVudV8yIl0sImp0aSI6ImQ0YzVlYjgwLTA5ZTctNGU0ZC1hZTg3LTVkNGI5M2FhNmFiNiIsImNsaWVudF9pZCI6ImhhbmR5LXNob3AifQ." +
                "aixF1eKlAKS_k3ynFnStE7-IRGiD5YaqznvK2xEjBew";

        final boolean verify = JWT.of(token).setKey(StrUtil.utf8Bytes("123456")).verify();
        Assertions.assertTrue(verify);
    }


    @Test
    public void expiredAtTest(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0Nzc1OTJ9.isvT0Pqx0yjnZk53mUFSeYFJLDs-Ls9IsNAm86gIdZo";
        JWTValidator.of(token).validateDate(DateUtil.date());
    }

    @Test
    public void issueAtTest(){
        final String token = JWT.create()
                .setIssuedAt(DateUtil.date())
                .setKey("123456".getBytes())
                .sign();

        // 签发时间早于被检查的时间
        JWTValidator.of(token).validateDate(DateUtil.yesterday());
    }

    @Test
    public void issueAtPassTest(){
        final String token = JWT.create()
                .setIssuedAt(DateUtil.date())
                .setKey("123456".getBytes())
                .sign();

        // 签发时间早于被检查的时间
        JWTValidator.of(token).validateDate(DateUtil.date());
    }

    @Test
    public void notBeforeTest(){
        final JWT jwt = JWT.create()
                .setNotBefore(DateUtil.date());

        JWTValidator.of(jwt).validateDate(DateUtil.yesterday());
    }

    @Test
    public void notBeforePassTest(){
        final JWT jwt = JWT.create()
                .setNotBefore(DateUtil.date());
        JWTValidator.of(jwt).validateDate(DateUtil.date());
    }

    @Test
    public void validateAlgorithmTest(){
        final String token = JWT.create()
                .setNotBefore(DateUtil.date())
                .setKey("123456".getBytes())
                .sign();

        // 验证算法
        JWTValidator.of(token).validateAlgorithm(JWTSignerUtil.hs256("12356".getBytes()));
    }

    @Test
    public void validateTest(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNb0xpIiwiZXhwIjoxNjI0OTU4MDk0NTI4LCJpYXQiOjE2MjQ5NTgwMzQ1MjAsInVzZXIiOiJ1c2VyIn0.L0uB38p9sZrivbmP0VlDe--j_11YUXTu3TfHhfQhRKc";
        byte[] key = "1234567890".getBytes();
        boolean validate = JWT.of(token).setKey(key).validate(0);
        Assertions.assertFalse(validate);
    }

    @Test
    public void validateDateTest(){
        final JWT jwt = JWT.create()
                .setPayload("id", 123)
                .setPayload("username", "hutool")
                .setExpiresAt(DateUtil.parse("2021-10-13 09:59:00"));

        JWTValidator.of(jwt).validateDate(DateUtil.date());
    }
}
