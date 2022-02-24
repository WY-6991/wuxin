package com.wuxin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxin.admin.mapper.UserMapper;
import com.wuxin.admin.pojo.User;
import com.wuxin.admin.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/11:22
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
