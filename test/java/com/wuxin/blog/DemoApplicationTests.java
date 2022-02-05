package com.wuxin.blog;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.AlgorithmUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.mode.SearchBlog;
import com.wuxin.blog.pojo.blog.*;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.*;
import com.wuxin.blog.utils.security.ShiroUtil;
import com.wuxin.blog.utils.string.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.yaml.snakeyaml.scanner.Constant;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private MomentService momentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CommentService commentService;


    @Test
    void test5() {
        // 测试redis

        // tagList
        // List<Tag> list = (List<Tag>) redisService.hget(RedisKey.TAG_LIST, RedisKey.TAG_LIST);
        // System.out.println(list);
        // category
        // List<Category> categoryList = (List<Category>) redisService.hget(RedisKey.CATEGORY_LIST, RedisKey.CATEGORY_LIST);
        // System.out.println(categoryList);
        //    blog list
        // IPage<Blog> blogIPage = (IPage<Blog>) redisService.hget(RedisKey.BLOG_LIST, 1 + "");
        // for (Blog record : blogIPage.getRecords()) {
        //     System.out.println(record);
        // }
        // System.out.println(blogIPage);


    }

    @Test
    void testUUID() {
        for (SearchBlog blog : blogService.searchBlog("服务器")) {
            System.out.println(blog);
        }

    }

    @Test
    void testJwt() {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, 10);

        Map<String, Object> payload = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();

        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put("username", "zhangsan");
        payload.put("motto", "user motto");
        payload.put("avatar", "user avatar");
        payload.put("email", "user email");
        payload.put("introduction", " user introduction");
        payload.put("roles", " user roles");

        headers.put("Authorization", payload);
        String key = "wuxin001";
        String token = JWTUtil.createToken(headers, payload, key.getBytes());
        System.out.println(token);
        validJwt(token);
    }

    void validJwt(String token) {
        String key = "wuxin001";
        JWT jwt = JWT.create().parse(token);
        JWTHeader header = jwt.getHeader();
        System.out.println("header" + header);
        String algorithm = jwt.getAlgorithm();
        System.out.println("algorithm" + algorithm);
        JWTPayload payload = jwt.getPayload();
        System.out.println("payload" + payload);


        boolean verifyKey = jwt.setKey(key.getBytes()).verify();
        System.out.println(verifyKey);

        boolean verifyTime = jwt.validate(0);
        System.out.println(verifyTime);
    }

    @Test
    void deleteRedisKeyCollection() {
        for (int i = 1; i < 100; i++) {
            boolean b = redisService.hHasKey(RedisKey.BLOG_LIST, i);
            System.out.println(i+"rediskey"+b);
            if (b) {
                // redisService.hdel(RedisKey.BLOG_LIST, i);
                System.out.println("rediskey"+b);
            } else {
                return;
            }
        }
    }


    @Test
    void testCodeToString() {
        String a = "123456";
        String b = ""+123456+"";
        System.out.println(a.equals(b));
    }


}
