package com.wuxin.blog.service.impl;


import com.wuxin.blog.mapper.HobbyMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.pojo.blog.Hobby;
import com.wuxin.blog.service.HobbyService;
import com.wuxin.blog.utils.MapperUtils;
import com.wuxin.blog.utils.ThrowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class HobbyServiceImpl implements HobbyService {

    @Autowired
    private HobbyMapper hobbyMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(Hobby hobby) {
        ThrowUtils.isNull(hobby.getUserId(), "获取不到用户Id");
        ThrowUtils.isNull(userMapper.selectById(hobby.getUserId()), "获取不到用户Id");
        ThrowUtils.ops(hobbyMapper.insert(hobby), "操作失败");
    }

    @Override
    public void update(Hobby hobby) {
        ThrowUtils.ops(hobbyMapper.updateById(hobby), "爱好不存在");
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(hobbyMapper.deleteById(id), "爱好不存在");
    }


    @Override
    public Hobby find(Long id) {
        ThrowUtils.ops(0, "该功能还未实现哦");
        return null;
    }

    @Override
    public List<Hobby> list() {
        ThrowUtils.ops(0, "该功能还未实现哦");
        return null;
    }

    @Override
    public List<Hobby> selectListByUserId(Long userId) {
        return MapperUtils.lambdaQueryWrapper(hobbyMapper).eq(Hobby::getUserId,userId).list();
    }




}
