package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    public void signUpTest () {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .account("sign-up-test-account")
                .f_password("1234")
                .s_password("1234")
                .name("sign up test")
                .build();

        logger.info(userRequestDto.toString());
        String singupResult = this.userService.signUp(userRequestDto);
        logger.info(singupResult);
    }
}
