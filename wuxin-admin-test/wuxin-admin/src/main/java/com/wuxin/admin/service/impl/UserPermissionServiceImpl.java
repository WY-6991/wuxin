package com.wuxin.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxin.admin.mapper.PermissionMapper;
import com.wuxin.admin.mapper.UserPermissionMapper;
import com.wuxin.admin.pojo.UserPermission;
import com.wuxin.admin.service.UserPermissionService;
import com.wuxin.admin.vo.UserPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/12:52
 * @Description:
 */
@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements UserPermissionService {

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<UserPermission> selectUserPermissionsByUserId(Long userId) {
        LambdaQueryChainWrapper<UserPermission> queryWrapper = new LambdaQueryChainWrapper<UserPermission>(userPermissionMapper);
        queryWrapper.eq(UserPermission::getUserId,userId);
        return queryWrapper.list();
    }

    @Override
    public UserPermissionVo selectUserPermissionVo(Long userId) {
        // 设置用户权限信息
        UserPermissionVo userPermissionVo = new UserPermissionVo();
        userPermissionVo.setUserid(userId);

        // 获取用户权限列表
        List<UserPermission> userPermissions = selectUserPermissionsByUserId(userId);
        // 遍历获取用户 permission content
        List<String> s = new ArrayList<>();
        userPermissions.forEach(p->{
           s.add(permissionMapper.selectById(p.getPid()).getContent());
        });
        // 设置
        userPermissionVo.setPermissionList(s);
        return userPermissionVo;
    }
}
