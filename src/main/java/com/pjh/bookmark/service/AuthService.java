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

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordEncoding passwordEncoding;

    public boolean accountValidator(String token){
        if(token != null){
            Token originToken = tokenRepository.findByUserId(this.tokenDecode(token));

            if(originToken.getUser() != null){
                if(originToken.getUser().getUserRole() < 10 ) {
                    return false;
                }
            }
            else {
                return false;
            }

            //만료 시간 체크
            if(originToken.getToken().equals(token) && originToken.getTokenExpire() == 1){
                Date now = new Date();
                long diff = (now.getTime() - originToken.getTokenTimestamp().getTime()) / (1000*60*60);

                if(diff > this.expireHourLimit){
                    originToken.setTokenExpire(0);
                    originToken.setToken("");
                    tokenRepository.save(originToken);
                    return false;
                }
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    private String tokenEncode(long userId) {
        TokenEncoding tokenEncoding =  new TokenEncoding();
        String rawToken = Integer.valueOf((int)(Math.random() * 1000000)) + "/" + Long.valueOf(userId) +"/bookmark";
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

                String offeredToken = tokenEncode(user.getUserId());

                authResponseDto.setToken(offeredToken);
                authResponseDto.setAccount(user.getUserAccount());

                Token token = tokenRepository.findByUserId(user.getUserId());

                if(token == null){
                    throw new UnAuthException();
                }

                token.setToken(offeredToken);
                token.setTokenExpire(1);
                token.setTokenTimestamp(new Date());
                tokenRepository.save(token);

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
            logoutToken.setTokenExpire(0);
            logoutToken.setToken("");
            tokenRepository.save(logoutToken);

            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
