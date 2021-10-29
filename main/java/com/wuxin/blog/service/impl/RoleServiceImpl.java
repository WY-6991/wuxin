package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.RoleMapper;
import com.wuxin.blog.pojo.Role;
import com.wuxin.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int addRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int delRole(Long id) {
        return roleMapper.deleteById(id);
    }

    @Override
    public boolean updateRole(Role role) {
        return new LambdaUpdateChainWrapper<Role>(roleMapper).eq(Role::getRoleId, role.getRoleId()).update();
    }

    @Override
    public IPage<Role> findRole(int current,int limit) {
        LambdaQueryChainWrapper<Role> roleLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(roleMapper);
        Page<Role> rolePage = new Page<>(current,limit);
        return roleLambdaQueryChainWrapper.page(rolePage);
    }

    @Override
    public Role findRoleByName(String roleName) {
        return new LambdaQueryChainWrapper<Role>(roleMapper).eq(Role::getRoleName,roleName).one();
    }

    @Override
    public Role findRoleById(Long roleId) {
        return roleMapper.selectById(roleId);
    }
}
