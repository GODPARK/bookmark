package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.AuthResponseDto;
import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    @Order(1)
    public void signUp () {
        String account = "sign-up-test-account";
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .account(account)
                .f_password("1234")
                .s_password("1234")
                .name("sign up test")
                .build();

        logger.info(userRequestDto.toString());
        User singupResult = this.userService.signUp(userRequestDto);
        logger.info(singupResult.toString());
        User user = this.userService.findUserByAccount(account);
        logger.info(user.toString());
        Assertions.assertEquals(user, singupResult);

        Token token = this.tokenService.findTokenByUserId(user.getUserId());
        logger.info(token.toString());
    }
}
