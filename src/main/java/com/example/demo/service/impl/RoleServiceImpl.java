package com.example.demo.service.impl;

import com.example.demo.common.Pages;
import com.example.demo.common.Params;
import com.example.demo.entity.Auth;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.util.ToolUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthRepository authRepository;

    @Override
    public Pages<Role> getRoles(Params params) {
        Page<Role> page = roleRepository.findAll(ToolUtil.getPageable(params));
        return new Pages<>(page.getNumber() + 1, page.getSize(), page.getContent(), page.getTotalElements());
    }

    @Override
    public void addRole(Role role) {
        Long id = role.getId();
        if (id != null) {
            Role role1 = roleRepository.findById(id).orElse(null);
            assert role1 != null;
            role.setCreateBy(role1.getCreateBy());
            role.setCreateTime(role1.getCreateTime());
        }
        roleRepository.save(role);
    }

    @Override
    public void addRoleAuth(Long roleId, ArrayList<Long> authIds) {
        Role role = roleRepository.findById(roleId).orElse(null);
        assert role != null;
        List<Auth> auths = authRepository.findAllById(authIds);
        if (role.getAuths() == null) {
            role.setAuths(new HashSet<>());
        }
        for (Auth auth : auths) {
            role.getAuths().add(auth);
        }
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void delRole(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        assert role != null;
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Set<Role> roles = user.getRoles();
            roles.remove(role);
            role.getAuths().clear();
            roleRepository.save(role);
            userRepository.save(user);
        }
        roleRepository.deleteById(id);
    }
}
