package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.ChatUrlMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.pojo.blog.ChatUrl;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.KeyUtil;
import com.wuxin.blog.utils.security.ShiroUtil;
import com.wuxin.blog.utils.ThrowUtils;
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

        ThrowUtils.isNull(user.getNickname(), "注册用户名不能为空！");
        // // 随机地址
        user.setAvatar(KeyUtil.randomUrl());
        // 随机生成盐
        Md5Hash md5Hash = new Md5Hash(user.getUsername());
        // 盐值加密之后的密码
        String saltPassword = ShiroUtil.salt(user.getPassword(), md5Hash.toHex());
        user.setPassword(saltPassword);
        user.setSalt(md5Hash.toHex()); // 得到盐加入到数据库
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
        // return userMapper.deleteById(id);
        ThrowUtils.ops(userMapper.deleteById(id), "该用户不存在！");
    }

    @Override
    public void updateUser(User user) {

        ThrowUtils.ops(userMapper.updateById(user), "该用户不存在！");
    }

    @Override
    public User findUserByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User findUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User finUserById(Long userId) {
        ThrowUtils.isNull(userId, "获取不到用户Id！");
        User user = userMapper.selectById(userId);
        ThrowUtils.isNull(user, "用户不存在！");
        return user;
    }

    @Override
    public User finUserByPhone(String phone) {
        return lambdaQueryWrapper(userMapper).eq(User::getPhone, phone).one();
    }

    @Override
    public IPage<User> findUser(Integer current, Integer size) {
        return lambdaQueryWrapper(userMapper).
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
        return lambdaQueryWrapper(userMapper).eq(User::getUsername, username).list();
    }


    @Override
    public IPage<User> finUserByKeywords(Integer current, Integer limit, String keywords) {
        Page<User> userPage = new Page<>(current, limit);
        return lambdaQueryWrapper(userMapper).select(User::getUserId,
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
    public boolean updatePass(Long userId, String username, String newUsername, String password) {
        if (Objects.equals(username, newUsername)) {
            return userUpdatePasswordUtils(userId, username, password);
        } else {
            return userUpdatePasswordUtils(userId, newUsername, password);
        }
    }

    @Override
    public boolean updatePass(String username, String password, User user) {
        return userUpdatePasswordUtils(user.getUserId(), username, password);
    }


    @Override
    public User findAdminUserInfo(Long adminUserId) {
        return lambdaQueryWrapper(userMapper).select(User::getNickname, User::getAvatar, User::getMotto)
                .eq(User::getUserId, adminUserId).one();
    }


    @Override
    public List<User> findCommentUserByUsernameOrEmail(String username, String email) {
        return lambdaQueryWrapper(userMapper).eq(User::getUsername, username).or().eq(User::getNickname, username).or().eq(User::getEmail, email).
                select(
                        User::getUserId, User::getUsername, User::getNickname, User::getEmail, User::getAvatar
                ).list();

    }


    @Override
    public User checkUsernameAndEmail(String nickname, String email) {
        return lambdaQueryWrapper(userMapper).eq(User::getNickname, nickname).eq(User::getEmail, email).select(
                User::getUserId, User::getUsername, User::getAvatar, User::getNickname, User::getEmail
        ).one();
    }


    @Override
    public User findUserDetail(Long userId) {
        User one = lambdaQueryWrapper(userMapper).eq(User::getUserId, userId).select(
                User::getUserId, User::getUsername, User::getAvatar, User::getNickname, User::getEmail, User::getMotto, User::getPhone
        ).one();
        ThrowUtils.isNull(one, "用户不存在！");
        return one;
    }

    @Override
    public Long getUserId(String username, String email) {
        List<User> userOr = findCommentUserByUsernameOrEmail(username, email);
        Long userId = null;
        // 判断用户是否存在
        if (userOr.size() == 0) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            // 将初始密码设置为用户邮箱
            newUser.setPassword(email);
            userId = addUser(newUser);
        } else {
            // 判断用户名和邮箱是否输入正确
            User userAnd = checkUsernameAndEmail(username, email);
            if (userAnd != null) {
                userId = userAnd.getUserId();
            }
        }
        return userId;
    }

    public Boolean userUpdatePasswordUtils(Long userId, String username, String password) {
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.finUserById(userId);
        // 获取salt
        String salt = new Md5Hash(username).toHex();
        if (!Objects.equals(username, user.getUsername())) {
            // 设置新的设置新的用户名
            user.setUsername(username);
            // 得到盐加入到数据库
            user.setSalt(salt);
        }

        // 使用salt加密密码
        String saltPassword = ShiroUtil.salt(password, salt);
        user.setPassword(saltPassword);

        return new LambdaUpdateChainWrapper<User>(userMapper).eq(User::getUserId, user.getUserId()).update(user);
    }

    public static LambdaQueryChainWrapper<User> lambdaQueryWrapper(UserMapper userMapper){
        return new LambdaQueryChainWrapper<>(userMapper);
    }

    public static <T> LambdaQueryChainWrapper<T> lambdaQueryWrapper(BaseMapper<T> baseMapper){
        return new LambdaQueryChainWrapper<T>(baseMapper);
    }
}
