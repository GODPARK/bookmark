package com.pjh.bookmark.service;

import com.pjh.bookmark.common.ErrorCode;
import com.pjh.bookmark.common.StatusCode;
import com.pjh.bookmark.component.PasswordEncoding;
import com.pjh.bookmark.component.TokenEncoding;
import com.pjh.bookmark.dto.AuthRequestDto;
import com.pjh.bookmark.dto.AuthResponseDto;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.exception.CustomException;
import com.pjh.bookmark.exception.UnAuthException;
import com.pjh.bookmark.exception.UnExpectedException;
import com.pjh.bookmark.repository.TokenRepository;
import com.pjh.bookmark.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Service
public class AuthService {

    @Value("${auth.expire.hour.limit}")
    private long expireHourLimit;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoding passwordEncoding;

    public boolean accountValidator(String token) {
        if (token != null) {
            Token originToken = tokenService.findTokenByUserId(this.tokenDecode(token));

            if (originToken.getUser().getUserRole() < 10) return false;
            if (!originToken.getToken().equals(token)) return false;

            //만료 시간 체크
            if (this.isTokenExpired(originToken)) {
                originToken.setTokenExpire(StatusCode.DEACTIVE_TOKEN_STATE.getState());
                tokenRepository.save(originToken);
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isTokenExpired(Token checkToken) {
        Date now = new Date();
        long diff = (now.getTime() - checkToken.getTokenTimestamp().getTime()) / (1000 * 60 * 60);
        return (diff > this.expireHourLimit || checkToken.getTokenExpire() == 0);
    }

    private String tokenEncode(long userId) {
        TokenEncoding tokenEncoding = new TokenEncoding();
        String rawToken = Integer.valueOf((int) (Math.random() * 1000000)) +
                "/" + Long.valueOf(userId) +
                "/bookmark" + Integer.valueOf((int) (Math.random() * 10000));
        return tokenEncoding.encrypt(rawToken);
    }

    public long tokenDecode(String token) {
        try {
            TokenEncoding tokenEncoding = new TokenEncoding();
            String rawToken = tokenEncoding.decrypt(token);
            String[] decodedToken = rawToken.split("/");
            if (decodedToken.length <= 1) {
                throw new UnAuthException();
            }
            int userId = Integer.valueOf(decodedToken[1]);
            return userId;
        } catch (IllegalArgumentException iae) {
            throw new UnAuthException();
        }
    }

    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        logger.info("login try Account:" + authRequestDto.getAccount());
        User user = userService.findUserByAccount(authRequestDto.getAccount());

        if (!passwordEncoding.matches(authRequestDto.getPassword(), user.getUserPassword())) {
            throw new CustomException(ErrorCode.ACCOUNT_OR_PASSWORD_IS_INCORRECT);
        }
        AuthResponseDto authResponseDto = new AuthResponseDto();
        Token token = tokenService.findTokenByUserId(user.getUserId());
        //token 만료되 었는지 검사
        if (!this.isTokenExpired(token)) {
            //만료되지 않았다면, 기존 토큰 전달
            authResponseDto.setToken(token.getToken());
        } else {
            //만료되었을 경우 토큰 재생성
            String offeredToken = this.tokenEncode(user.getUserId());
            authResponseDto.setToken(offeredToken);
            tokenRepository.save(
                    Token.builder()
                            .userId(user.getUserId())
                            .token(offeredToken)
                            .tokenExpire(StatusCode.ACTIVE_TOKEN_STATE.getState())
                            .tokenTimestamp(new Date())
                            .build()
            );
        }
        authResponseDto.setAccount(user.getUserAccount());
        logger.info("login success Account:" + authRequestDto.getAccount());
        return authResponseDto;
    }

    public String logout(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getHeader("auth_token") != null) {
            String token = httpServletRequest.getHeader("auth_token");
            long userId = this.tokenDecode(token);

            Token logoutToken = tokenService.findTokenByUserId(userId);
            logoutToken.setTokenExpire(StatusCode.DEACTIVE_TOKEN_STATE.getState());
            logoutToken.setToken("");
            tokenRepository.save(logoutToken);

            return "logout is success";
        } else {
            throw new CustomException(ErrorCode.EMPTY_AUTH_TOKEN_IN_HEADER);
        }
    }

}
