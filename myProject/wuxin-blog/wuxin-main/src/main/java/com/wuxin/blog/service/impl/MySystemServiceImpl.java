package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wuxin.blog.mapper.GithubSettingMapper;
import com.wuxin.blog.mapper.MySystemMapper;
import com.wuxin.blog.mapper.WebFooterLabelMapper;
import com.wuxin.blog.pojo.system.GithubSetting;
import com.wuxin.blog.pojo.system.MySystem;
import com.wuxin.blog.pojo.system.WebFooterLabel;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.MySystemService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/12/21/11:26
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class MySystemServiceImpl implements MySystemService {


    @Autowired
    private MySystemMapper mySystemMapper;

    @Autowired
    private WebFooterLabelMapper webFooterLabelMapper;

    @Autowired
    private GithubSettingMapper githubSettingMapper;


    private static final String LABEL = RedisKey.SYSTEM_FOOTER_LABEL;

    private static final String SYSTEM_INFO = "system_info";

    private static final String GITHUB_REPO_INFO = "github_repo_info";

    @Autowired
    private RedisService redisService;

    @Override
    public MySystem findMySystem(Integer id) {
        // 从redis中获取系统信息
        boolean b = redisService.hasKey(SYSTEM_INFO);
        if (b) {
            // 判断系统信息是否为空
            MySystem o = (MySystem) redisService.get(SYSTEM_INFO);
            if (StringUtils.isNotNull(o)) {
                // 返回从redis数据库获取的的系统信息
                return o;
            }
        }
        MySystem mySystem = mySystemMapper.selectById(id);
        System.out.println("=======================mysql system info ===================================");
        ThrowUtils.isNull(mySystem, "系统配置信息不存在！");
        // 将系统信息存入redis中
        redisService.set(SYSTEM_INFO, mySystem);
        return mySystem;
    }



    @Override
    public void updateMySystem(MySystem mySystem) {
        // 判断我的系统配置信息是否存在 存在 修改,不能存在就添加
        mySystem.setId(MySystem.SYSTEM_ID);
        if (mySystemMapper.selectById(MySystem.SYSTEM_ID) == null) {
            // 添加
            ThrowUtils.ops(mySystemMapper.insert(mySystem), "修改失败！系统配置信息不存在！");
        } else {
            // 修改
            ThrowUtils.ops(mySystemMapper.updateById(mySystem), "修改失败！系统配置信息不存在！");
        }
        // 设置新的系统信息
        redisService.set(SYSTEM_INFO, mySystem);
    }

    @Override
    public List<WebFooterLabel> findWebFooterLabel() {
        // 从redis中获取footer label
        boolean b = redisService.hasKey(LABEL);
        if (b) {
            // 判断是否还有值
            List<WebFooterLabel> list = (List<WebFooterLabel>) redisService.get(LABEL);
            if (list.size() != 0) {
                return list;
            }
        }
        System.out.println("=======================mysql footer label===================================");
        LambdaQueryChainWrapper<WebFooterLabel> webFooterLabelLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(webFooterLabelMapper);
        List<WebFooterLabel> list = webFooterLabelLambdaQueryChainWrapper.list();
        // 将list数据存入redis中
        redisService.set(LABEL, list);
        return list;
    }

    @Override
    public List<WebFooterLabel> findWebFooterLabel(int id) {
        LambdaQueryChainWrapper<WebFooterLabel> webFooterLabelLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(webFooterLabelMapper);
        return webFooterLabelLambdaQueryChainWrapper.eq(WebFooterLabel::getId, id).list();
    }

    @Override
    public void delWebFooterLabel(Integer id) {
        ThrowUtils.ops(webFooterLabelMapper.deleteById(id), "删除失败！标签不存在！");
        List<WebFooterLabel> list = findWebFooterLabel();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                // 从redis中移除这个标签
                list.remove(i);
                redisService.set(LABEL, list);
            }
        }
    }

    @Override
    public void addWebFooterLabel(WebFooterLabel webFooterLabel) {
        ThrowUtils.ops(webFooterLabelMapper.insert(webFooterLabel), "添加失败！标签不存在！");
        List<WebFooterLabel> list = findWebFooterLabel();
        list.add(webFooterLabel);
        redisService.set(LABEL, list);
    }

    @Override
    public void updateWebFooterLabel(WebFooterLabel webFooterLabel) {
        // 判断id是否存在
        ThrowUtils.ops(webFooterLabelMapper.updateById(webFooterLabel), "添加失败！标签不存在！");
        List<WebFooterLabel> list = findWebFooterLabel();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(webFooterLabel.getId())) {
                // 从redis中移除这个标签 然后在添加新标签内容
                list.remove(i);
                list.add(webFooterLabel);
                redisService.set(LABEL, list);
            }
        }


    }

    @Override
    public void updateGithubSetting(GithubSetting githubSetting) {
        if (!githubSetting.getId().equals(1)) {
            githubSetting.setId(1);
        }
        if (findGithubSetting(1) == null) {
            ThrowUtils.ops(githubSettingMapper.insert(githubSetting), "github仓库配置修改失败！");
        } else {
            ThrowUtils.ops(githubSettingMapper.updateById(githubSetting), "github仓库配置修改失败！");

        }
        // 不管是添加还是修改重新配置redis中github配置信息
        redisService.set(GITHUB_REPO_INFO, githubSetting);

    }

    @Override
    public GithubSetting findGithubSetting(Integer id) {
        // 检查redis中是否有github_respo的key
        boolean b = redisService.hasKey(GITHUB_REPO_INFO);
        if (b) {
            // 检查信息是否为空
            GithubSetting o = (GithubSetting) redisService.get(GITHUB_REPO_INFO);
            if (StringUtils.isNotNull(o)) {
                return o;
            }
        }
        GithubSetting githubSetting = githubSettingMapper.selectById(id);
        ThrowUtils.isNull(githubSetting, "github仓库配置不能为空！");
        // 将github配置信息存入redis中
        redisService.set(GITHUB_REPO_INFO, githubSetting);
        return githubSetting;
    }
}
