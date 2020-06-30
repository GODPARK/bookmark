package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.AdminUserRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    //TODO: 개발 필요 유저 권한 변경 용 서비스
    public ResponseEntity userRoleChange(AdminUserRequestDto adminUserRequestDto, long userId){
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
