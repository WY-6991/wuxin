package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.RoleMapper;
import com.wuxin.blog.pojo.blog.Role;
import com.wuxin.blog.service.RoleService;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void add(Role role) {
        ThrowUtils.ops(roleMapper.insert(role),"添加失败");
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(roleMapper.deleteById(id),"角色不存在");
    }

    @Override
    public void update(Role role) {
        ThrowUtils.ops(roleMapper.updateById(role),"角色不存在");
    }

    @Override
    public IPage<Role> selectListByPage(Integer current,Integer limit) {
        Page<Role> rolePage = new Page<>(current,limit);
        return MapperUtils.lambdaQueryWrapper(roleMapper).page(rolePage);
    }


    @Override
    public Role find(Long id) {
        return roleMapper.selectById(id);
    }



    @Override
    public Role findRoleByName(String roleName) {
        return MapperUtils.lambdaQueryWrapper(roleMapper).eq(Role::getRoleName,roleName).one();
    }

    @Override
    public List<Role> list() {
        ThrowUtils.ops(0, "该功能还未实现哦");
        return null;
    }


    @Override
    public IPage<Role> selectListByPage(Integer current, Integer limit, String keywords) {
        ThrowUtils.ops(0, "该功能还未实现哦");
        return null;
    }


}
