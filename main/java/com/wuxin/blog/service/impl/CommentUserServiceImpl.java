package com.wuxin.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.vo.CommentUserMapper;
import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.service.CommentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
    public CommentUser findCommentUserByUsernameOrEmail(String username, String email) {
        LambdaQueryChainWrapper<CommentUser> wrapper = new LambdaQueryChainWrapper<CommentUser>(commentUserMapper);
        return wrapper.eq(CommentUser::getUsername, username).or().eq(CommentUser::getEmail, email).one();

    }


    @Override
    public CommentUser checkUsernameAndEmail(String username, String email) {
        LambdaQueryChainWrapper<CommentUser> wrapper = new LambdaQueryChainWrapper<>(commentUserMapper);
        return wrapper.eq(CommentUser::getUsername, username).eq(CommentUser::getEmail, email).one();
    }

    @Override
    public Long getUserId(String username, String email) {
        CommentUser commentUserByUsernameAndEmail = findCommentUserByUsernameOrEmail(username, email);
        Long userId = null;
        // 判断用户是否存在
        if (commentUserByUsernameAndEmail == null) {
            CommentUser commentUser = new CommentUser();
            commentUser.setUsername(username);
            commentUser.setEmail(email);
            commentUser.setAvatar("https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202110/20211011221540.png");
            // 随机获取头像

            // 获取userId
            userId = addUser(commentUser);

        } else {
            // 判断用户名和邮箱是否输入正确
            CommentUser checkUsernameAndEmail = checkUsernameAndEmail(username, email);
            if (checkUsernameAndEmail != null) {
                userId = checkUsernameAndEmail.getUserId();
            }
        }

        System.out.println("userId=>"+userId);

        return userId;
    }

    @Override
    public CommentUser findUserById(Long userId) {


        return commentUserMapper.selectById(userId);
    }
}
