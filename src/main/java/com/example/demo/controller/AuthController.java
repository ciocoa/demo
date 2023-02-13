package com.example.demo.controller;

import com.example.demo.common.Pages;
import com.example.demo.common.Params;
import com.example.demo.common.Result;
import com.example.demo.entity.Auth;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/list")
    public Result<Pages<Auth>> getAuths(Params params) {
        return new Result<>(Result.SUCCESS, authService.getAuths(params), Result.MESSAGE);
    }

    @PreAuthorize("hasAnyAuthority('AUTH_WRITE')")
    @PostMapping("/auth/add")
    public Result<String> addAuth(@RequestBody Auth auth) {
        authService.addAuth(auth);
        return new Result<>(Result.SUCCESS, null, Result.MESSAGE);
    }

    @DeleteMapping("/auth/del")
    public Result<String> delAuth(@RequestParam Long id) {
        authService.delAuth(id);
        return new Result<>(Result.SUCCESS, null, Result.MESSAGE);
    }

}
