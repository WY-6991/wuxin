package com.wuxin.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuxin.blog.pojo.blog.Friend;
import com.wuxin.blog.pojo.blog.FriendMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/10:15
 * @Description:
 */
@Repository
public interface FriendMapper extends BaseMapper<Friend> {


    /**
     * 查看友情链接信息
     * @param id id
     * @return DTO
     */
    @Select("select * from wx_friend_message where id = #{id}")
    FriendMessage selectFriendMessage(@Param("id") Long id);

    /**
     * 修改友情链接信息通知
     * @param friendMessage message
     */
    @Update("UPDATE wx_friend_message SET content = #{friendMessage.content},comment_enabled = #{friendMessage.commentEnabled} WHERE id = #{friendMessage.id};")
    void updateFriendMessage(@Param("friendMessage") FriendMessage friendMessage);


    /**
     * 添加友情链接信息通知
     * @param friendMessage message
     */
    @Insert("INSERT INTO wx_friend_message (content,comment_enabled) VALUES (#{friendMessage.content},#{friendMessage.commentEnabled});")
    void addFriendMessage(@Param("friendMessage") FriendMessage friendMessage);
}
