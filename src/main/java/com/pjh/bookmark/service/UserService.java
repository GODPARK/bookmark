package com.pjh.bookmark.service;

import com.pjh.bookmark.common.ConstantCode;
import com.pjh.bookmark.common.ErrorCode;
import com.pjh.bookmark.component.PasswordEncoding;
import com.pjh.bookmark.dto.ResponseDto;
import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.exception.CustomException;
import com.pjh.bookmark.repository.TokenRepository;
import com.pjh.bookmark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoding passwordEncoding;

    public String signUp(UserRequestDto userRequestDto) {

        if (userRepository.countByUserAccount(userRequestDto.getAccount()) != 0) {
            throw new CustomException(ErrorCode.USER_ACCOUNT_IS_OVERLAP);
        }
        if (!userRequestDto.getF_password().equals(userRequestDto.getS_password())) {
            throw new CustomException(ErrorCode.USER_PASSWORD_NOT_MATCH_WITH_SECOND);
        }

        User saveUser = userRepository.save(
                User.builder()
                        .userAccount(userRequestDto.getAccount())
                        .userName(userRequestDto.getName())
                        .userAgree(userRequestDto.getAgree())
                        .userPassword(passwordEncoding.encode(userRequestDto.getF_password()))
                        .userRole(ConstantCode.DEFAULT_USER_ROLE.getState())
                        .state(ConstantCode.ACTIVE_USER_STATE.getState())
                        .userCreate(new Date())
                        .build()
        );

        tokenRepository.save(
                Token.builder()
                .userId(saveUser.getUserId())
                .token("")
                .tokenExpire(ConstantCode.DEACTIVE_TOKEN_STATE.getState())
                .tokenTimestamp(new Date())
                .build()
        );
        return "OK";
    }
}
