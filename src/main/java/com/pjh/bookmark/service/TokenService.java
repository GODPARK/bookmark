package com.pjh.bookmark.service;

import com.pjh.bookmark.common.ErrorCode;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.exception.CustomException;
import com.pjh.bookmark.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    public Token findTokenByUserId(long userId) {
        User user = this.userService.findUserById(userId);
        Optional<Token> token = this.tokenRepository.findByUserId(user.getUserId());
        if (token.isPresent()) {
            return token.get();
        } else {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }
}
