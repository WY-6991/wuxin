package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.vo.FriendComment;
import com.wuxin.blog.pojo.vo.FriendCommentReply;

public interface FriendCommentService {

    void addFriendComment(FriendComment comment);

    void updateFriendComment(FriendComment comment);

    void delFriendComment(Long commentId);


    IPage<FriendComment> findFriendComment(int current,int limit,String keywords);

    /*-------------------------------reply------------------------------------*/

    void addFriendCommentReply(FriendCommentReply reply);

    void updateFriendCommentReply(FriendCommentReply reply);

    void delFriendCommentReply(Long replyId);

    void delFriendCommentReplyByCommentId(Long id);
}
