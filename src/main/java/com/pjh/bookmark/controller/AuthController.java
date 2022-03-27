package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.AuthRequestDto;
import com.pjh.bookmark.dto.AuthResponseDto;
import com.pjh.bookmark.dto.ResponseDto;
import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.service.AuthService;
import com.pjh.bookmark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/sign-in", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDto> postSignInApi(@RequestBody @Valid AuthRequestDto authRequestDto){
        return ResponseEntity.ok().body(authService.login(authRequestDto));
    }

    @PostMapping(value="/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> postSignUpApi(@RequestBody @Valid UserRequestDto userRequestDto){
        return ResponseEntity.ok().body(userService.signUp(userRequestDto));
    }

    @PostMapping(value="/logout", produces = "application/json")
    public ResponseEntity<String> postLogoutApi(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok().body(authService.logout(httpServletRequest));
    }
}
