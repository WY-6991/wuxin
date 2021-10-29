package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.CommentUser;

import java.util.List;

public interface CommentUserService {

    Long addUser(CommentUser commentUser);

    CommentUser findUserById(Long userId);

    void updateUser(CommentUser commentUser);

    void delUser(Long userId);

    CommentUser findCommentUserByUsernameAndEmail(String username, String email);

    IPage<CommentUser> findCommentUser(int current,int limit,String keywords);

    CommentUser checkUsernameAndEmail(String replyNickName, String replyEmail);
}
