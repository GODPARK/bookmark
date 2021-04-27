package com.pjh.bookmark.interceptor;

import com.pjh.bookmark.exception.UnAuthException;
import com.pjh.bookmark.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Inet4Address;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private AuthService authService;

    public AuthInterceptor(AuthService authService){
        this.authService = authService;
    }
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler){
        String authToken = httpServletRequest.getHeader("auth_token");
        String remotePath = httpServletRequest.getRequestURI();
        String remoteUser = httpServletRequest.getRemoteHost();
        if(authToken == null || authToken.equals("")) throw new UnAuthException("auth token is missing", remotePath, remoteUser);
        if(this.authService.accountValidator(authToken)){
            return true;
        }
        else{
            throw new UnAuthException("auth info is invalid", remotePath, remoteUser);
        }
    }
}
