package com.example.demo.controller;

import com.example.demo.common.Params;
import com.example.demo.common.Result;
import com.example.demo.common.Pages;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/list")
    public Result<Pages<User>> getUsers(Params params) {
        return new Result<>(Result.SUCCESS, userService.getUsers(params), Result.MESSAGE);
    }

    @GetMapping("/user/get")
    public Result<Optional<User>> getUser(Long id) {
        return new Result<>(Result.SUCCESS, userService.getUserById(id), Result.MESSAGE);
    }

    @PreAuthorize("hasAnyAuthority('USER_WRITE')")
    @PostMapping("/user/add")
    public Result<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new Result<>(Result.SUCCESS, null, Result.MESSAGE);
    }

    @PutMapping("/user/role/add")
    public Result<String> addUserRole(@RequestParam Long userId, @RequestParam ArrayList<Long> roleIds) {
        userService.addUserRole(userId, roleIds);
        return new Result<>(Result.SUCCESS, null, Result.MESSAGE);
    }

    @DeleteMapping("/user/del")
    public Result<String> delUser(@RequestParam Long id) {
        userService.delUser(id);
        return new Result<>(Result.SUCCESS, null, Result.MESSAGE);
    }

}
