package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.mapper.ChatUrlMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.mode.UserComment;
import com.wuxin.blog.mode.UserPass;
import com.wuxin.blog.pojo.blog.ChatUrl;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.KeyUtil;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.security.ShiroUtil;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ChatUrlMapper chatUrlMapper;

    @Override
    public Long addUser(User user) {
        // 默认为普通用户
        user.setRoleId(1L);
        // 默认不禁用
        user.setStatus(1);
        //默认昵称为用户名
        user.setNickname(user.getUsername());
        // 随机图像
        user.setAvatar(KeyUtil.randomUrl());
        // 盐值加密之后的密码
        String salt = ShiroUtil.createSalt(user.getUsername());
        user.setPassword(ShiroUtil.salt(user.getPassword(), salt));
        user.setSalt(salt);
        // 添加用户
        userMapper.insert(user);
        //添加用户信息表
        ChatUrl chatUrl = new ChatUrl();
        chatUrl.setUserId(user.getUserId());
        chatUrlMapper.insert(chatUrl);
        return user.getUserId();
    }

    @Override
    public void delUser(Long id) {
        /*如果用户删除了,其用户关联的信息表应该删除！*/
        LambdaQueryChainWrapper<ChatUrl> chatUrlLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(chatUrlMapper);
        chatUrlLambdaQueryChainWrapper.eq(ChatUrl::getUserId, id);
        chatUrlMapper.delete(chatUrlLambdaQueryChainWrapper);
        ThrowUtils.ops(userMapper.deleteById(id), "该用户不存在！");
    }

    @Override
    public void updateUser(User user) {
        ThrowUtils.ops(userMapper.updateById(user), "该用户不存在！");
    }

    @Override
    public User findUserByEmail(String email) {
        return MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getEmail, email).one();
    }

    @Override
    public User findUserByUsername(String username) {
        return MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getUsername, username).one();
    }

    @Override
    public User finUserById(Long userId) {
        User user = userMapper.selectById(userId);
        ThrowUtils.isNull(user, "用户不存在！");
        return user;
    }

    @Override
    public User finUserByPhone(String phone) {
        return MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getPhone, phone).one();
    }

    @Override
    public IPage<User> findUser(Integer current, Integer size) {
        return MapperUtils.lambdaQueryWrapper(userMapper).
                select(User::getUserId,
                        User::getUsername,
                        User::getEmail,
                        User::getNickname,
                        User::getMotto,
                        User::getPhone,
                        User::getRoleId,
                        User::getAvatar,
                        User::getCreateTime).page(new Page<User>(current, size));
    }

    @Override
    public int countUser() {
        return userMapper.selectCount(null);
    }


    @Override
    public List<User> selectUser(String username) {
        return MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getUsername, username).list();
    }


    @Override
    public IPage<User> finUserByKeywords(Integer current, Integer limit, String keywords) {
        Page<User> userPage = new Page<>(current, limit);
        return MapperUtils.lambdaQueryWrapper(userMapper).select(User::getUserId,
                        User::getUsername,
                        User::getEmail,
                        User::getNickname,
                        User::getMotto,
                        User::getPhone,
                        User::getRoleId,
                        User::getAvatar,
                        User::getCreateTime)
                .like(!keywords.isEmpty(), User::getUsername, keywords)
                .or().like(!keywords.isEmpty(), User::getEmail, keywords)
                .or().like(!keywords.isEmpty(), User::getNickname, keywords)
                .page(userPage);

    }


    @Override
    public IPage<User> finUserByKeywords(PageVo pageVo) {
        Page<User> userPage = new Page<>(pageVo.getCurrent(), pageVo.getLimit());
        return MapperUtils.lambdaQueryWrapper(userMapper).select(User::getUserId,
                        User::getUsername,
                        User::getEmail,
                        User::getNickname,
                        User::getMotto,
                        User::getPhone,
                        User::getRoleId,
                        User::getAvatar,
                        User::getCreateTime)
                .like(StringUtils.isNotEmpty(pageVo.getKeywords()), User::getUsername, pageVo.getKeywords())
                .or().like(StringUtils.isNotEmpty(pageVo.getKeywords()), User::getEmail, pageVo.getKeywords())
                .or().like(StringUtils.isNotEmpty(pageVo.getKeywords()), User::getNickname, pageVo.getKeywords())
                .le(StringUtils.isNotEmpty(pageVo.getEnd()), User::getCreateTime, pageVo.getEnd())
                .ge(StringUtils.isNotEmpty(pageVo.getStart()), User::getCreateTime, pageVo.getStart())
                .page(userPage);

    }

    @Override
    public boolean updatePass(Long loginUserId, UserPass user) {
        User loginUser = finUserById(loginUserId);
        // 判断密码是否一致
        if (user.getNewPassword().equals(user.getOldPassword())) {
            throw new CustomException("修改失败,原密码和新密码一致！");
        }
        // 判断旧密码和数据库密码是否一致
        String oldPassword = ShiroUtil.salt(user.getOldPassword(), loginUser.getSalt());
        String newPassword = ShiroUtil.salt(user.getNewPassword(), loginUser.getSalt());
        boolean passwordEqual = loginUser.getPassword().equals(oldPassword);
        // 如果密码输入错误
        if (!passwordEqual) {
            throw new CustomException("原密码校验不通过！");
        }

        // 用户名是否修改了
        boolean usernameEqual = loginUser.getUsername().equals(user.getUsername());
        if (usernameEqual) {
            // 如果用户名没有修改，数据库中盐不需要修改直接修改用户密码就可以了
            loginUser.setPassword(newPassword);
        }else {

            String salt = ShiroUtil.createSalt(user.getUsername());
            newPassword = ShiroUtil.salt(user.getNewPassword(),salt);
            loginUser.setUsername(user.getUsername());
            loginUser.setPassword(newPassword);
            loginUser.setSalt(salt);
        }

        return MapperUtils.lambdaUpdateChainWrapper(userMapper).eq(User::getUserId,loginUserId).update(loginUser);
    }

    @Override
    public boolean updatePasswordByEmail(String email, String newPassword) {
        User user = findUserByEmail(email);
        String saltPassword = ShiroUtil.salt(newPassword, user.getEmail());
        user.setPassword(saltPassword);
        return MapperUtils.lambdaUpdateChainWrapper(userMapper).eq(User::getEmail,email).update(user);
    }


    @Override
    public User findAdminUserInfo(Long adminUserId) {
        return MapperUtils.lambdaQueryWrapper(userMapper).select(User::getNickname, User::getAvatar, User::getMotto)
                .eq(User::getUserId, adminUserId).one();
    }


    @Override
    public List<User> findCommentUserByUsernameOrEmail(String username, String email) {
        return MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getUsername, username).or().eq(User::getNickname, username).or().eq(User::getEmail, email).
                select(
                        User::getUserId, User::getUsername, User::getNickname, User::getEmail, User::getAvatar
                ).list();

    }


    @Override
    public User checkUsernameAndEmail(String nickname, String email) {
        return MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getNickname, nickname).eq(User::getEmail, email).select(
                User::getUserId, User::getUsername, User::getAvatar, User::getNickname, User::getEmail, User::isSubscription
        ).one();
    }


    @Override
    public User findUserDetail(Long userId) {
        User one = MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getUserId, userId).select(
                User::getUserId, User::getUsername, User::getAvatar, User::getNickname, User::getEmail, User::getMotto, User::getPhone
        ).one();
        ThrowUtils.isNull(one, "用户不存在！");
        return one;
    }

    @Override
    public Long getCommentUserId(String username, String email, boolean subscription) {
        List<User> userOr = findCommentUserByUsernameOrEmail(username, email);
        UserComment userComment = new UserComment();
        // 判断用户是否存在
        Long userId = null;
        if (userOr.size() == 0) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setSubscription(subscription);
            // 将初始密码设置为用户邮箱
            newUser.setPassword(email);
            userId = addUser(newUser);
        } else {
            // 用户名和邮箱是否输入正确
            User userAnd = checkUsernameAndEmail(username, email);
            if (StringUtils.isNotNull(userAnd)) {
                // 订阅推送消息改变了，需要重新设置推送消息
                if (userAnd.isSubscription() != subscription) {
                    updateUser(userAnd);
                }
                userId = userAnd.getUserId();
            }
        }
        return userId;
    }


    @Override
    public UserComment getReplyCommentUser(String username, String email, boolean subscription, boolean type) {
        List<User> userOr = findCommentUserByUsernameOrEmail(username, email);
        UserComment userComment = new UserComment();
        userComment.setType(type);
        // 判断用户是否存在
        if (userOr.size() == 0) {
            // 初始密码为用户邮箱
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setSubscription(subscription);
            userComment.setUserId(addUser(newUser));
            userComment.setSubscription(subscription);

        } else {
            // 用户名和邮箱输入正确
            User userAnd = checkUsernameAndEmail(username, email);
            if (StringUtils.isNotNull(userAnd)) {
                // 订阅推送消息改变了，需要重新设置推送消息
                System.out.println("userAnd,isSubscription" + userAnd.isSubscription());
                if (userAnd.isSubscription() != subscription) {
                    System.out.println("用户退订了修改信息");
                    updateUser(userAnd);
                }
                userComment.setUserId(userAnd.getUserId());
            }
        }
        return userComment;
    }

    @Override
    public User selectCommentUserByUserId(Long commentUserId) {
        return MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getUserId, commentUserId).select(User::getNickname, User::getEmail, User::getUserId, User::isSubscription).one();
    }

    @Override
    public IPage<User> selectUserRoleList(PageVo pageVo) {
        return MapperUtils.lambdaQueryWrapper(userMapper)
                .eq(StringUtils.isNotNull(pageVo.getId()), User::getRoleId, pageVo.getId())
                .like(StringUtils.isNotEmpty(pageVo.getKeywords()), User::getNickname, pageVo.getKeywords())
                .orderByDesc(User::getRoleId)
                .select(User::getUserId, User::getAvatar, User::getUsername, User::getNickname, User::getRoleId)
                .page(new Page<>(pageVo.getCurrent(), pageVo.getLimit()));
    }

    @Override
    public void updateUserRole(User user) {
        MapperUtils.lambdaUpdateChainWrapper(userMapper).eq(User::getUserId, user.getUserId()).update(user);
    }

    @Override
    public User findUserByNickName(String nickname) {
        return MapperUtils.lambdaQueryWrapper(userMapper).eq(User::getNickname,nickname).one();
    }
}
