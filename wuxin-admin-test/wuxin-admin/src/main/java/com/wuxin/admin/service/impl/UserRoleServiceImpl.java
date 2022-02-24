package com.wuxin.admin.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxin.admin.mapper.RoleMapper;
import com.wuxin.admin.mapper.UserRoleMapper;
import com.wuxin.admin.pojo.UserPermission;
import com.wuxin.admin.pojo.UserRole;
import com.wuxin.admin.service.UserRoleService;
import com.wuxin.admin.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/12:53
 * @Description:
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<UserRole> selectUserRolesByUserId(Long userId) {
        LambdaQueryChainWrapper<UserRole> queryWrapper = new LambdaQueryChainWrapper<UserRole>(userRoleMapper);
        queryWrapper.eq(UserRole::getUserId,userId);
        return queryWrapper.list();
    }

    @Override
    public UserRoleVo selectUserRoleVo(Long userId) {
        UserRoleVo userRoleVo = new UserRoleVo();
        userRoleVo.setUserId(userId);

        List<UserRole> userRoles = selectUserRolesByUserId(userId);
        ArrayList<String> strings = new ArrayList<>();
        userRoles.forEach(userRole -> {
            strings.add(roleMapper.selectById(userRole.getRoleId()).getRoleName());
        });

        userRoleVo.setRoleNameList(strings);

        return userRoleVo;
    }
}
