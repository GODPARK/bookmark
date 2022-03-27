package com.pjh.bookmark.service;

import com.pjh.bookmark.common.RoleCode;
import com.pjh.bookmark.dto.AuthRequestDto;
import com.pjh.bookmark.dto.AuthResponseDto;
import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.entity.Token;
import com.pjh.bookmark.entity.User;
import com.pjh.bookmark.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    @Order(1)
    public void sampeDateMake() {
        String account = "login-test-account";
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .account(account)
                .f_password("1234")
                .s_password("1234")
                .name("login test")
                .build();
        User singupResult = this.userService.signUp(userRequestDto);
        logger.info(singupResult.toString());
        singupResult.setUserRole(RoleCode.USER_USER_ROLE.getRole());
        // 권한 변경
        this.userRepository.save(singupResult);
    }

    @Test
    @Order(2)
    public void loginTest() {
        AuthResponseDto authResponseDto = this.authService.login(
                AuthRequestDto.builder()
                .account("login-test-account")
                .password("1234")
                .build()
        );
        logger.info(authResponseDto.toString());
    }

    @Test
    @Order(3)
    public void tokenValidateTest() {
        AuthResponseDto authResponseDto = this.authService.login(
                AuthRequestDto.builder()
                        .account("login-test-account")
                        .password("1234")
                        .build()
        );
        logger.info(authResponseDto.toString());
        long userId = this.authService.tokenDecode(authResponseDto.getToken());
        logger.info(Long.toString(userId));

        Token token = tokenService.findTokenByUserId(userId);
        logger.info(token.toString());

        Assertions.assertTrue(this.authService.accountValidator(authResponseDto.getToken()));
    }


}
