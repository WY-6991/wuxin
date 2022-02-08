package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.pojo.User;
import com.wuxin.blog.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/12:24
 * @Description:
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
