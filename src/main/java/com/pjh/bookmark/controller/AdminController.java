package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.AdminUserRequestDto;
import com.pjh.bookmark.service.AdminService;
import com.pjh.bookmark.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bmk/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AuthService authService;

    @PostMapping(path = "/user/role", consumes = "application/json", produces = "application/json")
    public ResponseEntity postUserRoleChangeApi(@RequestBody AdminUserRequestDto adminUserRequestDto, @RequestHeader("auth_token") String token) {
        return adminService.userRoleChange(adminUserRequestDto,authService.tokenDecode(token));
    }
}
