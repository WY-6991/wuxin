package com.wuxin.blog.shiro;


import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.service.RoleService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.MySecurityUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * 自定义realm
 *
 * @author wuxin001
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录用户名
        User user = MySecurityUtils.getUser();
        if(StringUtils.isNull(user)){
           throw new UnauthenticatedException("获取不到用户信息，请登录！");
        }
        logger.info("登录之后的用户信息：{}",user);
        //
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String roleName = roleService.find(user.getRoleId()).getRoleName();
        user.setRoleName(roleName);
        ArrayList<String> roleList = new ArrayList<>();
        roleList.add(roleName);
        logger.info("当前用户角色信息：{}",roleList);
        // 添加权限
        info.addRoles(roleList);

        return info;


    }


    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 自定义实现UsernamePasswordToken接口
        UsernamePasswordToken jwtToken =(UsernamePasswordToken) token;
        User user = userService.findUserByUsername(jwtToken.getPrincipal().toString());
        if (StringUtils.isNull(user)) {
            logger.error("用户不存在");
            throw new UnknownAccountException("用户不存在");
        }
        if (user.getStatus().equals(0)) {
            logger.error("账号已锁定");
            throw new LockedAccountException("账号已锁定！");
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        // shiro 中session储存用户信息
        session.setAttribute(GlobalConstant.USER_LOGIN_SESSION, user);
        // 返回密码验证
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }











    //
    // /**
    //  * 授权
    //  *
    //  * @param principalCollection principalCollection
    //  * @return   AuthorizationInfo
    //  */
    // @Override
    // protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    //     // 获取登录用户名
    //     User user = MySecurityUtils.getUser();
    //     logger.info("登录之后的用户信息：{}",user);
    //     //
    //     SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    //     //
    //     // // 添加角色
    //     // // List<String> roleList = new ArrayList<>();
    //     // // roleList.add(roleService.find(user.getRoleId()).getRoleName());
    //     // simpleAuthorizationInfo.addRole(roleService.find(user.getRoleId()).getRoleName());
    //     String roleName = roleService.find(user.getRoleId()).getRoleName();
    //     ArrayList<String> roleList = new ArrayList<>();
    //     roleList.add(roleName);
    //     logger.info("当前用户角色信息：{}",roleList);
    //     // 添加权限
    //     simpleAuthorizationInfo.addRoles(roleList);
    //
    //     return simpleAuthorizationInfo;
    //
    //
    // }
    //
    //
    // /**
    //  * 认证 登录时 使用
    //  *
    //  * @param token token令牌
    //  * @return AuthenticationInfo
    //  */
    // @Override
    // protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    //     // JWT
    //     // JwtToken jwtToken =(JwtToken) token;
    //     String username = token.getPrincipal().toString();
    //     User user = userService.findUserByUsername(username);
    //     // 判断用户是否存在
    //     if (StringUtils.isNull(user)) {
    //         logger.error("用户不存在");
    //         throw new UnknownAccountException("用户不存在");
    //     }
    //     // 判断用户账号是否锁定
    //     if (user.getStatus() == 0) {
    //         logger.error("账号已锁定");
    //         throw new LockedAccountException("账号已锁定！");
    //     }
    //     // 将用户隐私信息隐藏
    //     user.setSalt(null);
    //     user.setPassword(null);
    //
    //     Subject subject = SecurityUtils.getSubject();
    //     Session session = subject.getSession();
    //     // shiro 中session储存用户信息
    //     session.setAttribute(GlobalConstant.USER_LOGIN_SESSION, user);
    //     // 返回密码验证
    //     return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
    // }



}
