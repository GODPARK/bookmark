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
                .addPathPatterns("/bmk/api/**")
                .excludePathPatterns("/bmk/auth/**", "/","/swagger-ui.html");
    }

}
