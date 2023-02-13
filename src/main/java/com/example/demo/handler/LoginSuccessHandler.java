package com.example.demo.handler;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.util.JWTUtil;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisUtil redisUtil;

    private final JWTUtil jwtUtil;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        String jwt = jwtUtil.createJWT(username);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        user.setLoginTime(new Date());
        redisUtil.setCacheObject("login:" + username, user);
        redisUtil.setCacheObject("token:" + username, jwt);
        Result<Map<String, String>> result = new Result<>(Result.SUCCESS, map, Result.MESSAGE);
        WebUtil.renderString(response, result);
    }

}
