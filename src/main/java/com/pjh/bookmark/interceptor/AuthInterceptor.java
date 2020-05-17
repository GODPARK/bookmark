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

        System.out.println(httpServletRequest.getCookies());
        if(this.authService.accountValidator(httpServletRequest.getHeader("auth_token"))){
            return true;
        }
        else{
            throw new UnAuthException();
        }
    }
}
