package com.wuxin.blog.service;

import com.wuxin.blog.pojo.blog.Role;
import com.wuxin.blog.mode.Base.PageService;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface RoleService extends PageService<Role> {


    /**
     * 查找roleName
     * @param roleName roleName
     * @return DTO
     */
    Role findRoleByName(String roleName);


}
