package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.mapper.WebFooterLabelMapper;
import com.wuxin.blog.pojo.system.GithubSetting;
import com.wuxin.blog.pojo.system.MySystem;
import com.wuxin.blog.pojo.system.WebFooterLabel;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/12/21/11:26
 * @Description:
 */
public interface MySystemService {


    /**
     * 我的系统信息
     * @param id id
     * @return DTO
     */
    MySystem findMySystem(Integer id);





    /**
     * 修改我的系统信息
     * @param mySystem DTO
     */
    void updateMySystem(MySystem mySystem);


    /**
     * 返回网站底部标签
     * @return list
     */
    List<WebFooterLabel> findWebFooterLabel();

    /**
     * 返回网站底部标签
     * @param id id
     * @return DTO
     */
    List<WebFooterLabel> findWebFooterLabel(int id);

    /**
     * 删除底部标签
     * @param id id
     */
    void delWebFooterLabel(Integer id);

    /**
     * 添加网站底部标签
     * @param webFooterLabel DTO
     */
    void addWebFooterLabel(WebFooterLabel webFooterLabel);

    /**
     * 修改网站底部标签
     * @param webFooterLabel DTO
     */
    void updateWebFooterLabel(WebFooterLabel webFooterLabel);


    /**
     * github配置
     * @param githubSetting DTO
     */
    void updateGithubSetting(GithubSetting githubSetting);

    /**
     * 获取github配置
     * @param id
     * @return
     */
    GithubSetting findGithubSetting(Integer id);

}
