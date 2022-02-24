package com.wuxin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxin.admin.mapper.RoleMapper;
import com.wuxin.admin.pojo.Role;
import com.wuxin.admin.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/12:51
 * @Description:
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
