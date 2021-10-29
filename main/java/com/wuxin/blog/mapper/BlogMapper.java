package com.wuxin.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.Blog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    //
    @Select(" SELECT * FROM wx_blog WHERE blog_id >= ((SELECT MAX(blog_id) FROM wx_blog)-(SELECT MIN(blog_id) FROM wx_blog)) * RAND() + (SELECT MIN(blog_id) FROM wuxin_db.wx_blog) limit 0,5")
    List<Blog> getRandomFiveBlog();

    // @Select("SELECT * FROM wx_blog WHERE blog_id >= (SELECT floor(RAND() * (SELECT MAX(blog_id) FROM wx_blog))) ORDER BY blog_id LIMIT 0,5")
    // List<Blog> getRandomFiveBlog();
}
