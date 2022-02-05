package com.wuxin.blog.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * 自定义角色过滤器
 * shiro原生的角色过滤器RolesAuthorizationFilter 默认是必须同时满足roles[admin,guest]才有权限，而自定义的zqRoles 只满足其中一个即可访问
 * ex: zqRoles[admin,guest]
 */

@Component
public class MyRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        Subject subject = getSubject(req, resp);
        String[] rolesArray = (String[]) mappedValue;
        // 没有角色限制，有权限访问
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (String s : rolesArray) {
            //若当前用户是rolesArray中的任何一个，则有权限访问
            if (subject.hasRole(s)) {
                return true;
            }
        }
        return false;
    }


}
