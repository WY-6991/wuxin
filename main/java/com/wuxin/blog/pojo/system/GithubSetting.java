package com.wuxin.blog.pojo.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: wuxin001
 * @Date: 2022/01/01/19:00
 * @Description: github 访问配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_github_repository")
public class GithubSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    public static  final Integer GITHUB_REPOSITORY_ID = 1;

    /**
     *id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加速访问上传api
     */
    private String api;

    /**
     * 访问api
     */
    private String accessApi;

    /**
     * 用户名
     */
    private String master;

    /**
     * 文件夹
     */
    private String folder;

    /**
     * 访问token
     */
    private String accessToken;


    /**
     * 仓库
     */
    private String repository;

}
