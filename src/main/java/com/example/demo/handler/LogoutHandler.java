package com.example.demo.handler;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LogoutHandler implements LogoutSuccessHandler {

    private final RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            redisUtil.delRedisCache("login:" + username);
            redisUtil.delRedisCache("token:" + username);
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        Result<String> result = new Result<>(Result.SUCCESS, null, Result.MESSAGE);
        WebUtil.renderString(response, result);
    }

}
