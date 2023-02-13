package com.example.demo.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entity.User;
import com.example.demo.util.JWTUtil;
import com.example.demo.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final RedisUtil redisUtil;

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (!StringUtils.hasText(auth)) {
            filterChain.doFilter(request, response);
            return;
        }
        String username;
        try {
            String token = auth.substring("Bearer ".length() - 1);
            DecodedJWT claims = jwtUtil.parseJWT(token.trim());
            username = claims.getAudience().stream().findFirst().orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("凭证不合法");
        }
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(redisUtil.getCacheObject("login:" + username), User.class);
        UsernamePasswordAuthenticationToken authenticationToken;
        try {
            authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } catch (Exception e) {
            throw new RuntimeException("用户未登录");
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

}
