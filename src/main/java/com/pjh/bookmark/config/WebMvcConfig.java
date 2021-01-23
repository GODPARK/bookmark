package com.pjh.bookmark.config;

import com.pjh.bookmark.interceptor.AuthInterceptor;
import com.pjh.bookmark.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthService authService;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(new AuthInterceptor(authService))
                .addPathPatterns("/api/v1/bookmark/**")
                .addPathPatterns("/api/v1/hash/**")
                .addPathPatterns("/api/v1/search/**")
                .addPathPatterns("/api/v1/admin/**")
                .excludePathPatterns("/api/v1/auth/**","/swagger-ui.html");
    }

}
