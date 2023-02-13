package com.example.demo.config;

import com.example.demo.filter.JWTAuthenticationFilter;
import com.example.demo.handler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    private final LoginSuccessHandler loginSuccessHandler;

    private final LoginFailureHandler loginFailureHandler;

    private final LogoutHandler logoutHandler;

    private final AccessHandler accessHandler;

    private final AuthenticationPoint authenticationPoint;

    private final static String[] URL_WHITELIST = {"/", "/login", "/logout", "/captcha"};

    /**
     * @return 加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @param configuration 认证配置
     * @return 认证管理器
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * @param http HttpSecurity
     * @return http 安全过滤
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                // 关闭 csrf
                .and().csrf().disable()
                // 禁用 Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 登录
                .and().formLogin().successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
                // 注销
                .and().logout().logoutSuccessHandler(logoutHandler)
                // 未认证和未授权
                .and().exceptionHandling().accessDeniedHandler(accessHandler).authenticationEntryPoint(authenticationPoint)
                // 鉴权
                .and().authorizeHttpRequests()
                // 放行接口
                .requestMatchers(URL_WHITELIST).permitAll()
                // 鉴权接口
                .anyRequest().authenticated()
                // 过滤器
                .and().addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class);
        return http.build();
    }

}
