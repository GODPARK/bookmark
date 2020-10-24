package com.pjh.bookmark.interceptor;

import com.pjh.bookmark.exception.UnAuthException;
import com.pjh.bookmark.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private AuthService authService;

    public AuthInterceptor(AuthService authService){
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler){
        String authToken = httpServletRequest.getHeader("auth_token");
        if(authToken == null || authToken.equals("")) throw new UnAuthException("token missing");
        if(this.authService.accountValidator(authToken)){
            return true;
        }
        else{
            throw new UnAuthException();
        }
    }
}
