package com.wuxin.admin.vo;

import com.wuxin.admin.pojo.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/14:18
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionVo {
    private Long userId;
    private List<String> permissionList;
}
