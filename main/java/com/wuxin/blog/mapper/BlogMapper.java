package com.wuxin.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuxin.blog.pojo.blog.Blog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Administrator
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    //
    @Select("SELECT * FROM wx_blog WHERE blog_id >= ((SELECT MAX(blog_id) FROM wx_blog)-(SELECT MIN(blog_id) FROM wx_blog)) * RAND() + (SELECT MIN(blog_id) FROM wuxin_db.wx_blog) limit 0,5")
    List<Blog> getRandomFiveBlog();


    @Select("select blog_id,title,user_id from wx_blog order by create_time  desc limit 3")
    List<Blog> newBlog();

    @Select("select blog_id,title,user_id from wx_blog where blog_id=(select max(blog_id) from wx_blog where blog_id< #{blogId}) ")
    Blog beforeBlog(Long blogId);

    @Select("select blog_id,title,user_id from wx_blog where blog_id=(select min(blog_id) from wx_blog  where blog_id>#{blogId}) ")
    Blog nextBlog(Long blogId);
}
