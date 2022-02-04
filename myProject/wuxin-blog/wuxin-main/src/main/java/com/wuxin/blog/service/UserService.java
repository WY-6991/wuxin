package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.mode.UserComment;
import com.wuxin.blog.mode.UserPass;
import com.wuxin.blog.pojo.blog.CommentReply;
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
     * 分页
     * @param pageVo
     * @return
     */
    IPage<User> finUserByKeywords(PageVo pageVo);









    /**
     * 密码修改 方式二
     */
    boolean updatePass(Long loginUserId, UserPass user);

    /**
     * 密码修改,邮箱方式修改
     */
    boolean updatePasswordByEmail(String email, String newPassword);

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
     * @param subscription 是否订阅消息
     * @return subscription
     */
    Long getCommentUserId(String username, String email, boolean subscription);


    /**
     * 判断用户是否注册
     * 如果注册了的用户需要用户名和邮箱方式登录
     * @param username 用户名
     * @param email 邮箱
     * @param subscription 是否订阅消息
     * @param type 类型
     * @return
     */
    UserComment getReplyCommentUser(String username, String email, boolean subscription,boolean type);


    /**
     * 获取用户详情信息
     * @param userId
     * @return
     */
    User findUserDetail(Long userId);


    /**
     * 消息订阅与发布处理
     * @param commentUserId 获取被回复用户信息
     * @return user
     */
    User selectCommentUserByUserId(Long commentUserId);


    /**
     * 用户角色表
     */
    IPage<User> selectUserRoleList(PageVo pageVo);


    /**
     * 用户角色修改
     * @param user
     */
    void updateUserRole(User user);
}
