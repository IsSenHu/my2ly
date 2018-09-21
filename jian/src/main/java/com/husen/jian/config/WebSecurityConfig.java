package com.husen.jian.config;

import com.husen.jian.filter.JwtAuthenticationTokenFilter;
import com.husen.jian.jwt.JwtAuthenticationEntryPoint;
import com.husen.jian.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: 胡森
 * @Description: spring security 配置，项目启动的时候自动配置好已设置的权限
 * @Date: Created in 0:54 2018/5/4
 * @Modified By: 胡森
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserService userService;

    @Value("${jwt.exceptUrl}")
    private String exceptUrl;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserService userService, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userService = userService;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    /**
     * 用户名密码认证方法
     * @param authenticationManagerBuilder authenticationManagerBuilder
     * @throws Exception 异常
     */
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /**
     * 装载BCrypt密码编码器
     * @return BCrypt密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * token请求授权
     * @param httpSecurity httpSecurity
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //因为使用的是jwt,我们这里不需要csrf
                .csrf().disable()
                //未授权处理
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                //基于token,所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于获取token的rest api要允许匿名访问
                .antMatchers(exceptUrl).permitAll()
                //除上面的所有请求全部需要鉴权认证
                .antMatchers("/testRole").hasAuthority("admin")
                .anyRequest().authenticated();
        //添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //禁用缓存
        httpSecurity.headers().cacheControl();
    }
}
