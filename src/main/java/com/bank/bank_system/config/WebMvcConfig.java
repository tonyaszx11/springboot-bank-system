package com.bank.bank_system.config;

import com.bank.bank_system.interceptor.CheckLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private CheckLoginInterceptor checkLoginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkLoginInterceptor).addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/users/register",
                        "/users/login",
                        "/**/*.html",
                        "/**/*.js",
                        "/**/*.css"
                );
    }


}
