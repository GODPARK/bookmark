package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.AuthRequestDto;
import com.pjh.bookmark.dto.AuthResponseDto;
import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.service.AuthService;
import com.pjh.bookmark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/sign-in", consumes = "application/json", produces = "application/json")
    public AuthResponseDto signIn(@RequestBody AuthRequestDto authRequestDto){
        return authService.login(authRequestDto);
    }

    @PostMapping(value="/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity signUp(@RequestBody UserRequestDto userRequestDto){
        return userService.signUp(userRequestDto);
    }

    @PostMapping(value="/logout", produces = "application/json")
    public ResponseEntity logout(HttpServletRequest httpServletRequest){
        return authService.logout(httpServletRequest);
    }
}
