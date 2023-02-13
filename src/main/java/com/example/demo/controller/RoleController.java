package com.example.demo.controller;

import com.example.demo.common.Pages;
import com.example.demo.common.Params;
import com.example.demo.common.Result;
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/role/list")
    public Result<Pages<Role>> getRoles(Params params) {
        return new Result<>(Result.SUCCESS, roleService.getRoles(params), Result.MESSAGE);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_WRITE')")
    @PostMapping("/role/add")
    public Result<String> addRule(@RequestBody Role role) {
        roleService.addRole(role);
        return new Result<>(Result.SUCCESS, null, Result.MESSAGE);
    }

    @PutMapping("/role/auth/add")
    public Result<String> addRoleAuths(@RequestParam Long roleId, @RequestParam ArrayList<Long> authIds) {
        roleService.addRoleAuth(roleId, authIds);
        return new Result<>(Result.SUCCESS, null, Result.MESSAGE);
    }

    @DeleteMapping("/role/del")
    public Result<String> delRole(@RequestParam Long id) {
        roleService.delRole(id);
        return new Result<>(Result.SUCCESS, null, Result.MESSAGE);
    }

}
