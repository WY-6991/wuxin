package com.wuxin.blog.redis;

import com.wuxin.blog.utils.string.StringUtils;

/**
 * @Author: wuxin001
 * @Date: 2022/01/01/22:33
 * @Description:
 */
public class RedisKey {

    /**
     * 文章列表
     */
    public static final String BLOG_LIST = "blog_list";

    /**
     * 文章评论
     */
    public static final String COMMENT_LIST = "comment_list";


    /**
     * 文章列表
     */
    public static final String BLOG = "blog";

    /**
     * 文章统计
     */
    public static final String BLOG_COUNT = "blog_count";


    /**
     * 最新文章列表
     */
    public static final String NEW_BLOG_LSIT = "new_blog_list";


    /**
     * 友情链接
     */
    public static final String FRIEND_LIST = "friend_list";

    /**
     * 关于我的内容
     */
    public static final String ABOUT = "about";


    /**
     * category 全部
     */
    public static final String CATEGORY_LIST = "category_list";

    /**
     * catetgory 统计
     */
    public static final String CATEGORY_COUNT = "category_count";


    /**
     * category 全部
     */
    public static final String TAG_LIST = "tag_list";


    /**
     * 后台首页tag统计
     */
    public static final String TAG_COUNT = "tag_count";


    /**
     * 网站底部标签
     */
    public static final String SYSTEM_FOOTER_LABEL = "system_footer_label";


    /**
     * 博主信息
     */
    public static final String USER_INFO = "user_info";


    /**
     * 博主信息
     */
    public static final String BLOGGER_INFO = "blogger_info";


    /**
     * 网站更新修改内容
     */
    public static final String UPDATE_INFO = "update_info";


    /**
     * 首页壁纸
     */
    public static final String HOME_BACKGROUND_IMAGE = "home_background_image";

    /**
     * 动态
     */
    public static final String MOMENT_LIST = "moment_list";

    /**
     * 邮箱验证码接收
     */
    public static final String EMAIL_CODE = "email_code";


    /**
     * 归档列表一级标题
     */
    public static final String ARCHIVE_TITLE_LIST = "archive_title_list";


    /**
     * 归档列表二级标题
     */
    public static final String ARCHIVE_LIST = "archive_list";


    public static final String ACCESS_LOGIN_COUNT = "access_login_count";


    public static String getKey(Object id) {
        return "" + id;
    }

    public static String getKey(Object id, String keyName) {
        if (!StringUtils.isEmpty(keyName)) {
            keyName = "";
        }
        return keyName + "_" + id;
    }


    public static String getKey(Long blogId, String keyName, Integer current, Integer type) {
        if (blogId == null || blogId == 0) {
            return keyName + "_" + current + "_" + type;
        }
        return blogId + "_" + keyName + "_" + current + "_" + type;
    }


}
