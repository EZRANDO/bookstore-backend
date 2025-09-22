package com.example.bookstorebackend.common.config;

import com.example.bookstorebackend.common.interceptor.AdminOnlyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AdminOnlyInterceptor adminOnlyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // /api/admin/** 경로에만 적용
        registry.addInterceptor(adminOnlyInterceptor)
                .addPathPatterns("/api/admin/**");
    }
}
