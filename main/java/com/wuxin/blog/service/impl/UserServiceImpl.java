package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.BlogMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.pojo.User;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.util.ShiroUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;


    @Value("${user.avatar.location}")
    private String avatar;


    @Override
    public int addUser(User user) {
        // 默认为普通用户
        user.setRoleId(1L);
        // 默认不禁用
        user.setStatus(1);
        //默认昵称为用户名
        user.setNickname(user.getUsername());
        // 默认头像
        user.setAvatar(avatar);
        // 随机生成盐
        Md5Hash md5Hash = new Md5Hash(user.getUsername());
        // 盐值加密之后的密码
        String saltPassword = ShiroUtil.salt(user.getPassword(), md5Hash.toHex());
        user.setPassword(saltPassword);
        user.setSalt(md5Hash.toHex()); // 得到盐加入到数据库
        return userMapper.insert(user);
    }

    @Override
    public int delUser(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
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
        return userMapper.selectById(userId);
    }

    @Override
    public User finUserByPhone(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return userMapper.selectOne(queryWrapper);
    }
    @Override
    public IPage<User> findUser(Integer current, Integer size) {
        return new LambdaQueryChainWrapper<User>(userMapper).
                select(User::getUserId,
                        User::getUsername,
                        User::getEmail,
                        User::getNickname,
                        User::getMotto,
                        User::getPhone,
                        User::getRoleId,
                        User::getAvatar,
                        User::getCreateTime).page(new Page<User>(current,size));
    }

    @Override
    public int countUser() {
        return userMapper.selectCount(null);
    }


    @Override
    public List<User> selectUser(String username) {
        LambdaQueryChainWrapper<User> wrapper = new LambdaQueryChainWrapper<User>(userMapper);
        return wrapper.eq(User::getUsername, username).list();
    }


    @Override
    public IPage<User> selectUser2(String username) {
        LambdaQueryChainWrapper<User> wrapper = new LambdaQueryChainWrapper<User>(userMapper);
        Page<User> i = new Page<>(1,1);
        return wrapper.eq(User::getUsername, username).page(i);
    }


    @Override
    public IPage<User> finUserByKeywords(Integer current, Integer limit, String keywords) {
        LambdaQueryChainWrapper<User> chainWrapper = new LambdaQueryChainWrapper<>(userMapper);
        Page<User> userPage = new Page<>(current,limit);
        return chainWrapper.select(User::getUserId,
                User::getUsername,
                User::getEmail,
                User::getNickname,
                User::getMotto,
                User::getPhone,
                User::getRoleId,
                User::getAvatar,
                User::getCreateTime)
                .like(!keywords.isEmpty(),User::getUsername,keywords)
                .or().like(!keywords.isEmpty(),User::getEmail,keywords)
                .or().like(!keywords.isEmpty(),User::getNickname,keywords)
                .page(userPage);

    }

    @Override
    public boolean updatePass(String username, String password,User user) {
        // 根据用户名得到新的加载
        Md5Hash md5Hash = new Md5Hash(username);
        // 盐值加密之后的密码
        String saltPassword = ShiroUtil.salt(user.getPassword(), md5Hash.toHex());
        user.setUsername(username);
        user.setPassword(saltPassword);
        user.setSalt(md5Hash.toHex()); // 得到盐加入到数据库
        return new LambdaUpdateChainWrapper<User>(userMapper).eq(User::getUserId,user.getUserId()).update(user);
    }


    @Override
    public User findAdminUserInfo(Long adminUserId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.select(User::getNickname,User::getAvatar, User::getMotto)
                .eq(User::getUserId,adminUserId);
        return userMapper.selectOne(queryWrapper);
    }
}
