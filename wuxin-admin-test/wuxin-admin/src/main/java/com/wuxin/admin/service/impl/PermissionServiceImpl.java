package com.wuxin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxin.admin.mapper.PermissionMapper;
import com.wuxin.admin.pojo.Permission;
import com.wuxin.admin.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/12:52
 * @Description:
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
