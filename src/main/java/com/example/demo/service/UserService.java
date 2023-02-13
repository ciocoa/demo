package com.example.demo.service;

import com.example.demo.common.Params;
import com.example.demo.common.Pages;
import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserService {

    /**
     * 获取所有用户
     *
     * @param params 查询条件
     * @return 查询结果
     */
    Pages<User> getUsers(Params params);

    /**
     * 获取用户详情
     *
     * @param id 用户 ID
     * @return 用户信息
     */
    Optional<User> getUserById(Long id);

    /**
     * 添加用户
     *
     * @param user 用户信息
     */
    void addUser(User user);

    /**
     * 添加用户角色
     *
     * @param userId  用户 ID
     * @param roleIds 角色 ID 数组
     */
    void addUserRole(Long userId, ArrayList<Long> roleIds);

    /**
     * 删除用户
     *
     * @param id 用户 ID
     */
    void delUser(Long id);

}
