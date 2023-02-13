package com.example.demo.service.impl;

import com.example.demo.common.Pages;
import com.example.demo.common.Params;
import com.example.demo.entity.Auth;
import com.example.demo.entity.Role;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.AuthService;
import com.example.demo.util.ToolUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final RoleRepository roleRepository;

    @Override
    public Pages<Auth> getAuths(Params params) {
        Page<Auth> page = authRepository.findAll(ToolUtil.getPageable(params));
        return new Pages<>(page.getNumber() + 1, page.getSize(), page.getContent(), page.getTotalElements());
    }

    @Override
    public void addAuth(Auth auth) {
        Long id = auth.getId();
        if (id != null) {
            Auth auth1 = authRepository.findById(id).orElse(null);
            assert auth1 != null;
            auth.setCreateBy(auth1.getCreateBy());
            auth.setCreateTime(auth1.getCreateTime());
        }
        authRepository.save(auth);
    }

    @Override
    @Transactional
    public void delAuth(Long id) {
        Auth auth = authRepository.findById(id).orElse(null);
        assert auth != null;
        List<Role> roles = roleRepository.findAll();
        for (Role role : roles) {
            Set<Auth> auths = role.getAuths();
            auths.remove(auth);
            roleRepository.save(role);
        }
        authRepository.deleteById(id);
    }
}
