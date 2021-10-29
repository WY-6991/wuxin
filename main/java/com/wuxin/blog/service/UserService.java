package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.User;

import java.util.List;

public interface UserService {



    /**
     * 用户添加 注册
     * @param user DAO
     * @return 1
     */
    int addUser(User user);


    /**
     * 删除用户
     * @param id DAO
     * @return 1
     */
    int delUser(Long id);

    /**
     * 修改用户信息
     * @param user DTO
     * @return 1
     */
    int updateUser(User user);

    /**
     * 检查邮箱
     * @param email 邮箱
     * @return 1
     */
    User findUserByEmail(String email);

    /**
     * 检查用户名
     * @param username 用户名
     * @return 1
     */
    User findUserByUsername(String username);


    /**
     * 根据用户id查询信息
     * @param userId userid
     * @return user
     */
    User finUserById(Long userId);


    /**
     * 根据手机号查询用户信息
     * @param phone 手机号
     * @return user
     */
    User finUserByPhone(String phone);


    /**
     * 分页显示用户
     * @param current 页码
     * @param size 页面大小
     * @return ipage
     */
    IPage<User> findUser(Integer current,Integer size);


    /**
     * 统计用户总数
     * @return count
     */
    int countUser();


    /**
     * 查询user
     * @param username 用户名
     * @return list
     */
    List<User> selectUser(String username);



    IPage<User> selectUser2(String username);


    /**
     * 分页显示用户 搜索条件
     * @param current
     * @param limit
     * @param keywords 内容
     * @return
     */
    IPage<User> finUserByKeywords(Integer current, Integer limit, String keywords);


    /**
     * 修改密码
     * @param username
     * @param password
     */
    boolean updatePass(String username, String password,User user);

    /**
     * 获取我的信息
     * @param adminUserId
     * @return
     */
    User findAdminUserInfo(Long adminUserId);
}
