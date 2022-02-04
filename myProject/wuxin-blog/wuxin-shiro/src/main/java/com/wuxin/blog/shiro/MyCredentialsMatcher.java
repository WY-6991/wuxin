package com.wuxin.blog.shiro;


import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.utils.ShiroUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义 认证匹配
 *
 * @author wuxin001
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {


    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        User user = null;
        PrincipalCollection principals = info.getPrincipals();
        if (principals.asList().size() == 1) {
            for (Object principal : info.getPrincipals()) {
                user = (User) principal;
            }
        }
        String password = new String((char[]) token.getCredentials());

        // 自定义校验规则 将用户输入的密码和数据用户salt 得到加密后密码同数据加密后密码对比
        if (user == null) {
            return false;
        }
        // 判断是邮箱方式登录还是密码方式登录
        if (password.length() > 15) {
            return equals(password, user.getPassword());
        } else {
            String saltPwd = ShiroUtil.salt(password, user.getSalt());
            return equals(saltPwd, user.getPassword());
        }


    }


}