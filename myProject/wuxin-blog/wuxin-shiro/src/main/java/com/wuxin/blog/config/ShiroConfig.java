package com.wuxin.blog.config;


import com.wuxin.blog.filter.CorsAuthenticationFilter;
import com.wuxin.blog.redis.RedisShiroCacheManager;
import com.wuxin.blog.shiro.MyCredentialsMatcher;
import com.wuxin.blog.shiro.UserRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * shiro 配置
 *
 * @author Administrator
 */
@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Value("${spring.shiro.redis.host}")
    private String host;

    @Value("${spring.shiro.redis.port}")
    private Integer port;

    @Value("${spring.shiro.redis.database}")
    private Integer database;

    @Value("${spring.shiro.redis.password}")
    private String password;

    @Value("${spring.shiro.redis.timeout}")
    private Integer timeout;

    @Value("${spring.shiro.redis.expire}")
    private Integer expire;


    /**
     * anon 无需认证就可以访问
     * authc 需要认证才可以访问
     * user 必须拥有记住我功能才可以使用
     * role 拥有某个角色权限才可以使用
     * perms 用验某个资源权限才可以访问
     *
     * @param securityManager DefaultSecurityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultSecurityManager securityManager) {
        logger.info("===============================shiro过滤器启动===================================");
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 设置过滤器 所有过滤器都需要经过自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("corsAuthenticationFilter", corsAuthenticationFilter());
        // 所有请求经过自定义过滤器
        filterChainDefinitionMap.put("/", "corsAuthenticationFilter");
        // 无需登录就可以访问
        filterChainDefinitionMap.put("/**", "anon");
        // 拦截所哟/amdin下路径
        filterChainDefinitionMap.put("/admin/**", "authc");
        // 需要登录
        filter.setLoginUrl("/to/login");
        // 无权限
        filter.setUnauthorizedUrl("/no/role");
        // 注册过滤路径
        filter.setFilters(filterMap);
        // 注册过滤规则
        filter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 注册shiro管理器到过滤器中
        filter.setSecurityManager(securityManager);

        return filter;
    }


    /**
     * 配置安全管理器
     */
    @Bean(name = "securityManager")
    public DefaultSecurityManager getDefaultSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注册自定义realm
        securityManager.setRealm(userRealm);
        // 用户登录密码校验使用自定义密码校验规则
        userRealm.setCredentialsMatcher(myCredentialsMatcher());
        return securityManager;

    }

    /**
     * 自定义登录验证规则
     */
    @Bean
    public MyCredentialsMatcher myCredentialsMatcher() {
        return new MyCredentialsMatcher();
    }


    /**
     * shiro和自定义用户realm
     *
     * @return UserRealm
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm realm = new UserRealm();
        // 注册自定义缓存管理器 配置shiro-redis管理
        realm.setCacheManager(new RedisShiroCacheManager());
        // 开启注册redis缓存管理
        realm.setCachingEnabled(true);
        // 开启认证缓存管理
        // realm.setAuthenticationCachingEnabled(true);
        // 开启授权缓存管理
        // realm.setAuthorizationCachingEnabled(true);
        // 指定 缓存管理器实现授权认证
        // realm.setAuthorizationCacheName("");
        return realm;
    }


    /**
     * 注册自定义过滤器
     *
     * @return CorsAuthenticationFilter
     */
    @Bean
    public CorsAuthenticationFilter corsAuthenticationFilter() {
        return new CorsAuthenticationFilter();
    }


    /**
     * shiro注解支持
     * 只有配置了这个类，
     * shiro的注解才会生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * shiro注解支持
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }


    /**
     * Shiro生命周期处理器
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}

