package com.wuxin.blog.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.vo.CommentUserMapper;
import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.service.CommentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentUserServiceImpl implements CommentUserService {

    @Autowired
    private CommentUserMapper commentUserMapper;

    @Override
    public Long addUser(CommentUser commentUser) {
        commentUserMapper.insert(commentUser);
        return commentUser.getUserId();
    }

    @Override
    public void updateUser(CommentUser commentUser) {
        commentUserMapper.updateById(commentUser);
    }

    @Override
    public void delUser(Long userId) {
        commentUserMapper.deleteById(userId);
    }

    @Override
    public IPage<CommentUser> findCommentUser(int current, int limit, String keywords) {
        LambdaQueryChainWrapper<CommentUser> wrapper = new LambdaQueryChainWrapper<>(commentUserMapper);
        Page<CommentUser> page = new Page<>(current, limit);
        return wrapper.like(!keywords.isEmpty(), CommentUser::getUsername, keywords).page(page);
    }

    @Override
    public CommentUser findCommentUserByUsernameAndEmail(String username, String email) {
        LambdaQueryChainWrapper<CommentUser> wrapper = new LambdaQueryChainWrapper<>(commentUserMapper);
        return wrapper
                .eq(CommentUser::getUsername, username)
                .or()
                .eq(CommentUser::getEmail, email)
                .one();
    }


    @Override
    public CommentUser checkUsernameAndEmail(String username, String email) {
        LambdaQueryChainWrapper<CommentUser> wrapper = new LambdaQueryChainWrapper<>(commentUserMapper);
        return wrapper.eq(CommentUser::getUsername, username).eq(CommentUser::getEmail, email).one();
    }

    @Override
    public CommentUser findUserById(Long userId) {
        return commentUserMapper.selectById(userId);
    }
}
