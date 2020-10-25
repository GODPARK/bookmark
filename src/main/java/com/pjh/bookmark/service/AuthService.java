package com.pjh.bookmark.service;

import com.pjh.bookmark.component.PasswordEncoding;
import com.pjh.bookmark.component.TokenEncoding;
import com.pjh.bookmark.dto.AuthRequestDto;
import com.pjh.bookmark.dto.AuthResponseDto;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.exception.UnAuthException;
import com.pjh.bookmark.exception.UnExpectedException;
import com.pjh.bookmark.repository.TokenRepository;
import com.pjh.bookmark.repository.UserRepository;
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

    private static final int LIVE_TOKEN_STATE = 1;
    private static final int EXPIRE_TOKEN_STATE = 0;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoding passwordEncoding;

    public boolean accountValidator(String token){
        if(token != null){
            Token originToken = tokenRepository.findByUserId(this.tokenDecode(token));

            if(originToken.getUser() == null) return false;
            if(originToken.getUser().getUserRole() < 10 ) return false;
            if(!originToken.getToken().equals(token)) return false;

            //만료 시간 체크
            if(this.isTokenExpired(originToken)){
                originToken.setTokenExpire(EXPIRE_TOKEN_STATE);
                tokenRepository.save(originToken);
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isTokenExpired(Token checkToken) {
        Date now = new Date();
        long diff = (now.getTime() - checkToken.getTokenTimestamp().getTime()) / (1000*60*60);
        return (diff > this.expireHourLimit || checkToken.getTokenExpire() == 0);
    }

    private String tokenEncode(long userId) {
        TokenEncoding tokenEncoding =  new TokenEncoding();
        String rawToken = Integer.valueOf((int)(Math.random() * 1000000)) +
                "/" + Long.valueOf(userId) +
                "/bookmark" + Integer.valueOf((int)(Math.random() * 10000));
        return tokenEncoding.encrypt(rawToken);
    }

    public int tokenDecode(String token){
        try {
            TokenEncoding tokenEncoding = new TokenEncoding();
            String rawToken = tokenEncoding.decrypt(token);
            String[] decodedToken = rawToken.split("/");
            if (decodedToken.length <= 1) {
                throw new UnAuthException();
            }
            int userId = Integer.valueOf(decodedToken[1]);
            return userId;
        }
        catch(IllegalArgumentException iae) {
            throw new UnAuthException();
        }
    }

    public AuthResponseDto login(AuthRequestDto authRequestDto) throws UnAuthException {
        User user = userRepository.findByUserAccountAndState(authRequestDto.getAccount(),1);
        if(user == null){
            throw new UnAuthException("Account is Not found");
        }
        else{
            if(passwordEncoding.matches(authRequestDto.getPassword(),user.getUserPassword())){
                AuthResponseDto authResponseDto = new AuthResponseDto();
                Token token = tokenRepository.findByUserId(user.getUserId());
                if(token == null){
                    throw new UnAuthException();
                }
                //token 만료되 었는지 검사
                if(!this.isTokenExpired(token)) {
                    //만료되지 않았다면, 기존 토큰 전달
                    authResponseDto.setToken(token.getToken());
                }
                else {
                    //만료되었을 경우 토큰 재생성
                    String offeredToken = this.tokenEncode(user.getUserId());
                    authResponseDto.setToken(offeredToken);
                    token.setToken(offeredToken);
                    token.setTokenExpire(LIVE_TOKEN_STATE);
                    token.setTokenTimestamp(new Date());
                    tokenRepository.save(token);
                }
                authResponseDto.setAccount(user.getUserAccount());
                return authResponseDto;
            }
            else{
                throw new UnAuthException("Password is Fail");
            }
        }

    }

    public ResponseEntity logout(HttpServletRequest httpServletRequest){
        if(httpServletRequest.getHeader("auth_token") != null){
            String token = httpServletRequest.getHeader("auth_token");
            int userId = this.tokenDecode(token);

            Token logoutToken = tokenRepository.findByUserId(userId);
            logoutToken.setTokenExpire(EXPIRE_TOKEN_STATE);
            logoutToken.setToken("");
            tokenRepository.save(logoutToken);

            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
