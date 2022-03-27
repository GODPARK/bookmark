package com.pjh.bookmark.service;

import com.pjh.bookmark.common.RoleCode;
import com.pjh.bookmark.common.StatusCode;
import com.pjh.bookmark.common.ErrorCode;
import com.pjh.bookmark.component.PasswordEncoding;
import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.exception.CustomException;
import com.pjh.bookmark.repository.TokenRepository;
import com.pjh.bookmark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoding passwordEncoding;

    public User findUserById(long userId) {
        Optional<User> user = this.userRepository.findByUserIdAndState(userId, StatusCode.ACTIVE_USER_STATE.getState());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public User findUserByAccount(String account) {
        Optional<User> user = this.userRepository.findByUserAccountAndState(account, StatusCode.ACTIVE_USER_STATE.getState());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public User signUp(UserRequestDto userRequestDto) {

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
                        .userRole(RoleCode.DEFAULT_USER_ROLE.getRole())
                        .state(StatusCode.ACTIVE_USER_STATE.getState())
                        .userCreate(new Date())
                        .build()
        );

        tokenRepository.save(
                Token.builder()
                .userId(saveUser.getUserId())
                .token("")
                .tokenExpire(StatusCode.DEACTIVE_TOKEN_STATE.getState())
                .tokenTimestamp(new Date())
                .build()
        );
        return saveUser;
    }
}
