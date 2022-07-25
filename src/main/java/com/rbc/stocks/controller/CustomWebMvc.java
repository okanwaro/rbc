package com.rbc.stocks.controller;

import com.rbc.stocks.http.CustomHttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvc implements WebMvcConfigurer {
    @Autowired
    CustomHttpInterceptor customHttpInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customHttpInterceptor).addPathPatterns("/**");
    }
}
