package com.wuxin.blog.config.shiro;


import com.wuxin.blog.pojo.User;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.util.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("AuthorizationInfo 开始配置中...");
        Subject subject = SecurityUtils.getSubject();
        log.info("subject user=>{}", subject.getPrincipal());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 从数据库中授权
        User currentUser = (User) subject.getPrincipal();//拿到User对象
        return info;


    }


    /**
     * 认证
     *
     * @param token token令牌
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("正在登录...");
        String username = token.getPrincipal().toString();
        User user = userService.findUserByUsername(username);
        Subject subject = SecurityUtils.getSubject();
        log.info("AuthenticationInfo currentUser={}", subject.getPrincipal());
        if (user == null) { // 判断用户是否存在
            return null;
        }
        // 判断用户账号是否锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已锁定！");
        }
        // 用户登录的session
        Session session = subject.getSession();
        session.setAttribute(GlobalConstant.USER_LOGIN_SESSION, user);
        log.info("shiro user1={}",user);
        log.info("shiro session user={}",session.getAttribute(GlobalConstant.USER_LOGIN_SESSION));
        // 返回密码验证
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

}
