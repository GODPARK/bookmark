package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.AuthRequestDto;
import com.pjh.bookmark.dto.AuthResponseDto;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.exception.UnAuthException;
import com.pjh.bookmark.repository.TokenRepository;
import com.pjh.bookmark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    public void accountValidator(String token){
        System.out.println("valid check");
    }

    public AuthResponseDto login(AuthRequestDto authRequestDto){
        User user = userRepository.findByUserAccountAndState(authRequestDto.getAccount(),1);
        if(user == null){
            throw new UnAuthException("Account is Not found");
        }
        else{
            if(user.getUserPassword() == authRequestDto.getPassword()){
                AuthResponseDto authResponseDto = new AuthResponseDto();

                authResponseDto.setToken(tokenMaker(user.getUserId()));
                return authResponseDto;
            }
            else{
                throw new UnAuthException("Password is Fail");
            }
        }

    }

    private String tokenMaker(long userId){
        return "token";
    }
}
