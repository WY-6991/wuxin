package com.wuxin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuxin.admin.pojo.UserPermission;
import com.wuxin.admin.vo.UserPermissionVo;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/12:49
 * @Description:
 */
public interface UserPermissionService extends IService<UserPermission> {


    /**
     * 用户id 获取permissions
     * @param userId userid
     * @return list
     */
    List<UserPermission> selectUserPermissionsByUserId(Long userId);


    /**
     * 用户id 获取permissions
     * @param userId userid
     * @return list
     */
    UserPermissionVo selectUserPermissionVo(Long userId);
}
