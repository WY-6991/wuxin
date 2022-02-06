package com.wuxin.blog.constant;

/**
 * @author Administrator
 */
public class GlobalConstant {

    public static final String USER_LOGIN_SESSION = "userLoginSession";
    public static final Long ADMIN_USER_ID = 1L;



    /**
     * github文件上传url
     * 访问用户空间名
     * 访问仓库名
     * 访问文件加名称
     */
    public final static String GITHUB_URL = "https://api.github.com/repos/%s/%s/contents/%s/";

    /**
     * 访问路径
     * 访问用户空间名
     * 访问仓库名
     * 访问文件加名称
     */
    public final static String ACCESS_URL = "https://cdn.jsdelivr.net/gh/%s/%s/%s/";
}
