package com.wuxin.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuxin.blog.mode.SearchBlog;
import com.wuxin.blog.pojo.blog.Blog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Administrator
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    @Select("SELECT * FROM wx_blog WHERE publish = 1 and blog_id >= ((SELECT MAX(blog_id) FROM wx_blog)-(SELECT MIN(blog_id) FROM wx_blog)) * RAND() + (SELECT MIN(blog_id) FROM wuxin_db.wx_blog) limit 0,5")
    List<Blog> getRandomFiveBlog();

    @Select("select blog_id,title from wx_blog  where publish = 1 order by create_time desc limit 3")
    List<SearchBlog> newBlog();

    @Select("select blog_id,title from wx_blog where publish = 1 and blog_id=(select max(blog_id) from wx_blog where blog_id< #{blogId}) ")
    SearchBlog beforeBlog(Long blogId);

    @Select("select blog_id,title from wx_blog where publish = 1 and blog_id=(select min(blog_id) from wx_blog  where blog_id>#{blogId}) ")
    SearchBlog nextBlog(Long blogId);

    @Select("select blog_id,title,content,create_time from wx_blog where publish = 1 and (title like concat('%',#{keywords},'%') or content like concat('%',#{keywords},'%') ) ORDER BY create_time ")
    List<SearchBlog> searchBlog(String keywords);


}
