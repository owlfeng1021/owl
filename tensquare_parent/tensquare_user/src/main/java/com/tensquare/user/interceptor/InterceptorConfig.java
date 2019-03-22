package com.tensquare.user.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private  jwtInterceptor jwt;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
       //注册拦截器 要声明 要拦截的对象 和要拦截的请求
        registry.addInterceptor(jwt)
                .addPathPatterns("/**") //拦截所有的路径
                .excludePathPatterns("/**/login/**");//把login目录下的路径除外
    }
}
