package com.example.demo.service;

import com.example.demo.common.Pages;
import com.example.demo.common.Params;
import com.example.demo.entity.Auth;


public interface AuthService {

    /**
     * 获取所有权限
     *
     * @param params 查询条件
     * @return 查询结果
     */
    Pages<Auth> getAuths(Params params);

    /**
     * 添加角色
     *
     * @param auth 角色信息
     */
    void addAuth(Auth auth);

    /**
     * 删除角色
     *
     * @param id 角色 ID
     */
    void delAuth(Long id);

}
