package com.wuxin.blog.config.shiro;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;


@Slf4j
@Configuration
public class ShiroConfig {

    // 设置过滤器
    // @Autowired
    // private MyRolesAuthorizationFilter filter;


    /**
     * anon 无需认证就可以访问
     * authc 需要认证才可以访问
     * user 必须拥有记住我功能才可以使用
     * perms 用验某个资源权限才可以访问
     * role 拥有某个角色权限才可以使用
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultSecurityManager securityManager) {
        log.info("开始配置shiroFilter..........");
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        // 添加shiro内置过滤器
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        // 自定义角色过滤器
        HashMap<String, Filter> hashMap = new HashMap<>();
        hashMap.put("myRoles", new MyRolesAuthorizationFilter());
        bean.setFilters(hashMap);
        map.put("/**", "anon");
        //设置未授权的请求访问页面
        // bean.setUnauthorizedUrl("/Login/noauth");
        bean.setFilterChainDefinitionMap(map);
        // 设置安全管理器
        return bean;
    }

    @Bean(name = "securityManager")
    public DefaultSecurityManager getDefaultSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        log.info("开始配置securityManager....");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(userRealm);
        // 使用自定义配置的验证规则
        userRealm.setCredentialsMatcher(myCredentialsMatcher());
        // 记住我功能
        // securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;

    }

    /**
     * 自定义验证
     * @return
     */
    @Bean
    public MyCredentialsMatcher myCredentialsMatcher() {
        log.info("开始执行自定义配置验证规则...");
        return new MyCredentialsMatcher();
    }


    /**
     * shiro和自定义用户realm
     *
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        log.info("开始执行自定义userRealm...");
        return new UserRealm();
    }


    /**
     * shiro注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        log.info("开始执行注解生效 AuthorizationAttributeSourceAdvisor ... ");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * shiro注解支持
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        log.info("开始执行注解生效 AuthorizationAttributeSourceAdvisor... ");
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }


    // 配置记住我功能
    // @Bean
    // public SimpleCookie rememberMeCookie() {
    //     log.info("开启记住我功能的有效时间！");
    //     //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
    //     SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    //     // 记住我cookie生效时间 单位秒
    //     simpleCookie.setMaxAge(30 * 24 * 60 * 60); // 30天
    //     return simpleCookie;
    // }
    //
    // @Bean
    // public CookieRememberMeManager rememberMeManager() {
    //     log.info("开启记住我功能 ...");
    //     //System.out.println("ShiroConfiguration.rememberMeManager()");
    //     CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    //     cookieRememberMeManager.setCookie(rememberMeCookie());
    //     //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
    //     cookieRememberMeManager.setCipherKey(Base64.encode("pybbs is the best!".getBytes()));
    //     return cookieRememberMeManager;
    // }


    /**
     * 自定义过滤器
     * @return
     */
    @Bean
    public MyRolesAuthorizationFilter setMyRolesAuthorizationFilter() {
        log.info("配置自定义shiro过滤器...");
        return new MyRolesAuthorizationFilter();
    }

    /**
     * Shiro生命周期处理器
     *
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        log.info("LifecycleBeanPostProcessor...");
        return new LifecycleBeanPostProcessor();
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        log.info("配置redis缓存管理...");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        log.info("配置redis...");
        RedisManager redisManager = new RedisManager();
        redisManager.setDatabase(6);
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        log.info("配置redis-shrio session...");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        log.info("配置redis-shiro dao...");
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    // @Bean
    // public KickoutSessionControlFilter kickoutSessionControlFilter() {
    //     log.info("配置踢人 KickoutSessionControlFilter ...");
    //     KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
    //     kickoutSessionControlFilter.setCacheManager(cacheManager());
    //     kickoutSessionControlFilter.setSessionManager(sessionManager());
    //     kickoutSessionControlFilter.setKickoutAfter(false);
    //     kickoutSessionControlFilter.setMaxSession(1);
    //     kickoutSessionControlFilter.setKickoutUrl(pageJump.getIndex());
    //     return kickoutSessionControlFilter;
    // }
}

