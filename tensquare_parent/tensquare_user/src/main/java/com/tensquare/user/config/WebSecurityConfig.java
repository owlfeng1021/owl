package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
        // authorizeRequests 是security全注解配置的开端 表示开始需要的权限
        // 需要的权限需要两个部分 一个是拦截的部分 一个是访问路径需要的权限
        // antMatchers 表示拦截什么路径 permitAll 任何权限 都可以访问 直接放行所有
        // anyRequest()任何的请求 authentication认证之后开可以访问呢
        // and().csrf().disable() 是固定写法 表示使csrf拦截失效


    }
}
