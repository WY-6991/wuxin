package com.wuxin.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.*;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.*;
import com.wuxin.blog.utils.KeyUtil;
import com.wuxin.blog.utils.string.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.yaml.snakeyaml.scanner.Constant;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    void test() {
        String keywords = "";
        System.out.println(StringUtils.isNull(keywords));
    }


    @Test
    void test2() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        System.out.println(dateFormat.format(date));
    }

    @Test
    void test3() {
        Integer integer = blogService.blogCount();
        System.out.println(integer);
    }


    @Test
    void test4() {
        Integer blogId = null;
        System.out.println(String.valueOf(blogId).equals(""));
    }

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


}
