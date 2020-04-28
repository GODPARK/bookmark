package com.pjh.bookmark.service;

import com.pjh.bookmark.component.PasswordEncoding;
import com.pjh.bookmark.dto.AuthRequestDto;
import com.pjh.bookmark.dto.AuthResponseDto;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.exception.UnAuthException;
import com.pjh.bookmark.repository.TokenRepository;
import com.pjh.bookmark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordEncoding passwordEncoding;

    public void accountValidator(String token){
        System.out.println("valid check");
    }

    private String tokenMaker(long userId){
        return "token";
    }

    public AuthResponseDto login(AuthRequestDto authRequestDto) throws UnAuthException {
        User user = userRepository.findByUserAccountAndState(authRequestDto.getAccount(),1);
        if(user == null){
            throw new UnAuthException("Account is Not found");
        }
        else{
            if(passwordEncoding.matches(authRequestDto.getPassword(),user.getUserPassword())){
                AuthResponseDto authResponseDto = new AuthResponseDto();

                authResponseDto.setToken(tokenMaker(user.getUserId()));
                return authResponseDto;
            }
            else{
                throw new UnAuthException("Password is Fail");
            }
        }

    }

    public ResponseEntity logout(HttpServletRequest httpServletRequest){
        if(true){
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
