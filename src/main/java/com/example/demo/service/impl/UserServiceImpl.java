package com.example.demo.service.impl;

import com.example.demo.common.Pages;
import com.example.demo.common.Params;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.ToolUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public Pages<User> getUsers(Params params) {
        Page<User> page = userRepository.findAll(ToolUtil.getPageable(params));
        return new Pages<>(page.getNumber() + 1, page.getSize(), page.getContent(), page.getTotalElements());
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void addUser(User user) {
        Long id = user.getId();
        if (id == null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        } else {
            User user1 = userRepository.findById(id).orElse(null);
            assert user1 != null;
            user.setCreateBy(user1.getCreateBy());
            user.setCreateTime(user1.getCreateTime());
            if (user.getPassword() == null) {
                user.setPassword(user1.getPassword());
            } else {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(user.getPassword()));
            }
        }
        userRepository.save(user);
    }

    @Override
    public void addUserRole(Long userId, ArrayList<Long> roleIds) {
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        List<Role> roles = roleRepository.findAllById(roleIds);
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        for (Role role : roles) {
            user.getRoles().add(role);
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        user.getRoles().clear();
        userRepository.save(user);
        userRepository.deleteById(id);
    }

}
