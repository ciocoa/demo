package com.example.demo.service;

import com.example.demo.common.Pages;
import com.example.demo.common.Params;
import com.example.demo.entity.Role;

import java.util.ArrayList;

public interface RoleService {

    /**
     * 获取所有角色
     *
     * @param params 查询条件
     * @return 查询结果
     */
    Pages<Role> getRoles(Params params);

    /**
     * 添加角色
     *
     * @param role 角色信息
     */
    void addRole(Role role);

    /**
     * 添加角色权限
     *
     * @param roleId  角色 ID
     * @param authIds 权限 ID 数组
     */
    void addRoleAuth(Long roleId, ArrayList<Long> authIds);

    /**
     * 删除角色
     *
     * @param id 角色 ID
     */
    void delRole(Long id);

}
