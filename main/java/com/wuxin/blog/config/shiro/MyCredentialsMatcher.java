package com.wuxin.blog.config.shiro;


import com.wuxin.blog.pojo.User;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private UserService userService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        // 获取输入的密码
        String password = (String) getCredentials(info);
        log.info("MyCredentialsMatcher password = {}",password);
        // 加密方式
        String pwdType = String.valueOf(token.getPassword());
        log.info("MyCredentialsMatcher pwdType = {}",pwdType);
        if(pwdType.length()==32){ // 判断是否加密过
            return equals(pwdType,password);
        }else {
            // 获取用户名
            log.info("MyCredentialsMatcher username=>{}",token.getUsername());
            User user = userService.findUserByUsername(token.getUsername());
            if(user==null){
                return false;
            }
            String salt = user.getSalt();
            // 将用户输入后的密码同数据库比对
            String saltPwd = ShiroUtil.salt(pwdType,salt);
            log.info("密码比对结果...{}",equals(saltPwd,user.getPassword()));
            return equals(saltPwd,user.getPassword());

        }


    }
}