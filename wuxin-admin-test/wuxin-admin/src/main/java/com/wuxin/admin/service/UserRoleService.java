package com.wuxin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuxin.admin.pojo.UserRole;
import com.wuxin.admin.vo.UserRoleVo;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/12:49
 * @Description:
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户id获取用户权限
     * @param userId
     * @return
     */
    List<UserRole> selectUserRolesByUserId(Long userId);

    /**
     * 获取用户权限列表 名称
     * @param userId
     * @return
     */
    UserRoleVo selectUserRoleVo(Long userId);
}
