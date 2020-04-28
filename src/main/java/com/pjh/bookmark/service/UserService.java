package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.repository.TokenRepository;
import com.pjh.bookmark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private boolean signUpValidationCheck(UserRequestDto userRequestDto){
        if(userRequestDto.getAccount() == null){
            return false;
        }

        if(userRepository.countByUserAccount(userRequestDto.getAccount())!= 0) {
            return false;
        }

        if(!userRequestDto.getF_password().equals(userRequestDto.getS_password())){
            return false;
        }

        if(userRequestDto.getName() == null){
            return false;
        }
        return true;
    }

    public ResponseEntity signUp(UserRequestDto userRequestDto){
        if(this.signUpValidationCheck(userRequestDto)){
            User user = new User();
            user.setUserAccount(userRequestDto.getAccount());
            user.setUserName(userRequestDto.getName());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setUserPassword(passwordEncoder.encode(userRequestDto.getF_password()));
            user.setState(1);
            user.setUserCreate(new Date());
            user.setUserRole(1);

            User saveUser = userRepository.save(user);

            Token token = new Token();
            token.setUserId(saveUser.getUserId());
            token.setToken("");
            token.setTokenExpire(0);
            token.setTokenTimestamp(new Date());

            tokenRepository.save(token);

            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
