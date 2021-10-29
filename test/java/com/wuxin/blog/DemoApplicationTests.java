package com.wuxin.blog;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.*;
import com.wuxin.blog.service.*;
import com.wuxin.blog.util.IpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentUserService commentUserService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArchiveService archiveService;


    @Autowired
    private MomentService momentService;


    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    void contentLoads02() {

        List<User> admin = userService.selectUser(null);
        for (User user : admin) {
            System.out.println(user);
        }

    }

    @Test
    void contentLoads03() {

        IPage<User> iPage = userService.selectUser2("admin");
        System.out.println(iPage.getRecords());

    }

    @Test
    void contentLoads04() {
        IPage<Blog> blog = blogService.findBlog(1, 2);
        for (Blog record : blog.getRecords()) {
            System.out.println(record);
        }

    }

    /**
     * 测试自定义生成Id
     */
    @Test
    void contentLoads06() {

        Comment comment = new Comment();
        comment.setBlogId(1L); //评论
        comment.setContent("自定义ID生成测试");
        comment.setCommentUserId(1L);
        int i = commentService.addComment(comment);
        if (i > 0) System.out.println("添加成功");
        System.out.println("添加失败");


    }


    @Test
    void contentLoads05() {

        Date date = new Date();
        System.out.println(date);
        DateTime date1 = DateUtil.date(date);
        System.out.println(date1);
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        Long aLong = Long.getLong(format);
        System.out.println(format);
        System.out.println(aLong);
        System.out.println(Long.parseLong(format));
        Long l = Long.parseLong(format);
        System.out.println(l);
        // System.out.println(Integer.parseInt(String.valueOf(date)));

    }


    /**
     * 测试添加blog list tag
     */
    @Test
    void contentLoads07() {

        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("redis");
        tag.setTagId(2L);
        tags.add(tag);

        Tag tag2 = new Tag();
        tag2.setName("springboot");
        tag2.setTagId(3L);
        tags.add(tag);

        Blog blog = new Blog();
        blog.setUserId(1L);
        blog.setTitle("title");
        blog.setDescription("setDescription");
        blog.setContent("content");
        blog.setCid(2L);
        blog.setSecrecy("false");
        blog.setAppreciation("false");
        blog.setPassword("false");
        blog.setPublished("true");
        blog.setTop("false");
        blog.setViews(10000);
        blog.setWords(2000L);
        blog.setTags(tags);

        Long i = blogService.addBlog(blog);
        if (i != 0) {
            System.out.println("生成的blogID={}" + i);
        } else {
            System.out.println("添加失败！");
        }


    }

    @Test
    void getuser() {
        IPage<User> user = userService.findUser(1, 2);
        for (User record : user.getRecords()) {
            System.out.println(record);
        }
    }


    @Test
    void selectTagPageByName() {
        IPage<Tag> springboot = tagService.findTagByPage(1, 10, "s");
        for (Tag record : springboot.getRecords()) {
            System.out.println(record);
        }
    }


    @Test
    void finUserByKeywords() {
        IPage<User> iPage = userService.finUserByKeywords(1, 10, "a");
        for (User record : iPage.getRecords()) {
            System.out.println(record);
        }
    }


    @Test
    void testGetUserAddressByIp() {
        // 192.168.1.5
        // BaiduAddressUtil baiduAddressUtil = new BaiduAddressUtil();
        // String address = baiduAddressUtil.getAddress("192.168.1.5");
        // System.out.println(address);
        // http://1.117.46.114:9999/
        String cityInfo = IpUtil.getCityInfo("1.117.46.114");
        System.out.println("cityInfo=" + cityInfo);
    }


    @Test
    void findArchive() {
        IPage<Archive> archive = archiveService.findArchive(1, 4);
        archive.getRecords().forEach(a->{
            System.out.println(a);
        });
    }

    @Test
    void findArchiveList() {
        List<Archive> archiveList = archiveService.findArchiveList();
        archiveList.forEach(archive -> {
            System.out.println(archive);
        });
    }


    @Test
    void addMoment() {
        Moment moment = new Moment();
        moment.setContent("今夕是何夕，明月何其多");
        moment.setUserId(1L);
        int i = momentService.addMoment(moment);
        if(i==1){
            System.out.println("添加成功!");
        }
        else {
            System.out.println("添加失败！");
        }
    }

    @Test
    void getRandomBlog() {
        for (Blog blog : blogService.randomBlog()) {
            System.out.println("blog.getBlogId->"+blog.getBlogId());
        }

    }


    @Test
    void testCheckUsernameAndEmail() {
        CommentUser user = commentUserService.findCommentUserByUsernameAndEmail("水落", "21913777519@qq.com");
        System.out.println(user);

        CommentUser ch = commentUserService.checkUsernameAndEmail("水落", "21913777519@qq.com");
        System.out.println(ch);

    }



}
