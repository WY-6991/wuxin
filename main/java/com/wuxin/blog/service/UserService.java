package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.User;

import java.util.List;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface UserService {



    /**
     * 用户添加 注册
     * @param user DAO
     * @return 返回用户id
     */
    Long addUser(User user);


    /**
     * 删除用户
     * @param id DAO
     * @return 1
     */
    void delUser(Long id);

    /**
     * 修改用户信息
     * @param user DTO
     * @return 1
     */
    void updateUser(User user);

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




    /**
     * 分页显示用户 搜索条件
     * @param current 页码
     * @param limit 大小
     * @param keywords 关键字
     * @return page
     */
    IPage<User> finUserByKeywords(Integer current, Integer limit, String keywords);


    /**
     * 修改密码
     * @param username 用户名
     * @param newUsername 新的用户名
     * @param password 密码
     * @return 成功信息
     */
    boolean updatePass(Long userId,String username,String newUsername, String password);

    /**
     * 密码修改,邮箱方式修改
     * @param username 用户名
     * @param password 密码
     * @return 成功信息
     */
    boolean updatePass(String username, String password,User user);

    /**
     * 获取博主信息
     * @param adminUserId 我的id
     * @return DTO
     */
    User findAdminUserInfo(Long adminUserId);

    /**
     * 二者根据一个条件查询
     * @param username 用户名
     * @param email 邮箱
     * @return list
     */
    List<User> findCommentUserByUsernameOrEmail(String username, String email);

    /**
     * 同时查询
     * @param username 用户名
     * @param email 邮箱
     * @return DTO
     */
    User checkUsernameAndEmail(String username, String email);

    /**
     * 获取用户Id
     * @param username 用户名
     * @param email 邮箱
     * @return userID
     */
    Long getUserId(String username, String email);


    /**
     * 获取用户详情信息
     * @param userId
     * @return
     */
    User findUserDetail(Long userId);
}
