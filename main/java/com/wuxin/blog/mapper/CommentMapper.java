package com.wuxin.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuxin.blog.pojo.blog.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * @author Administrator
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {


    /**
     * 统计今日评论次数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 评论次数
     */
    @Select("select IFNULL(count(distinct r.create_time ),0) + IFNULL(concat(c.create_time),0) from wuxin_db.wx_comment_reply r ,wuxin_db.wx_comment c where (r.create_time >= #{start}  and r.create_time <=#{end}) and( c.create_time >= #{start}  and c.create_time <= #{end})")
    Integer todayCommentCount(@Param("start") String start, @Param("end") String end);
}
