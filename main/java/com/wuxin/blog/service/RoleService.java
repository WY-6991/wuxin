package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.Role;

import java.util.List;

public interface RoleService {

    /**
     * 添加角色
     * @param role roleDTO
     * @return int
     */
    int addRole(Role role);

    /**
     * 删除roleDTO
     * @param id roleId
     * @return int
     */
    int delRole(Long id);

    /**
     * 修改角色信息
     * @param role roleDTO
     * @return true
     */
    boolean updateRole(Role role);

    /**
     * 返回roleList
     * @return list
     */
    IPage<Role> findRole(int current,int limit);

    /**
     * 查找roleName
     * @param roleName roleName
     * @return DTO
     */
    Role findRoleByName(String roleName);

    /**
     * 显示role roleId
     * @param roleId roleId
     * @return DTO
     */
    Role findRoleById(Long roleId);
}
