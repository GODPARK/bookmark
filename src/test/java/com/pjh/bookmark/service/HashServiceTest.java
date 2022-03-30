package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.entity.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HashServiceTest {

    @Autowired
    private HashService hashService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookmarkService bookmarkService;

    private final String account = "sign-up-hashkey-account";
    private final String hashKeyName = "test-hash-key";
    private final String bookmarkName = "test-bookmark";
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    @Order(1)
    public void initTest () {
        logger.info("----------------------init hashkey test--------------------------");
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .account(this.account)
                .f_password("1234")
                .s_password("1234")
                .name("sign up test")
                .build();

        logger.info(userRequestDto.toString());
        User singupResult = this.userService.signUp(userRequestDto);
        logger.info(singupResult.toString());

        Bookmark bookmark = this.bookmarkService.createBookmarkFunc(
                Bookmark.builder()
                        .bookmarkName(this.bookmarkName)
                        .bookmarkInfo("bookmark test info")
                        .url("http://www.google.com")
                        .build(),
                singupResult.getUserId()
        );
        logger.info(bookmark.toString());
        Assertions.assertNotNull(bookmark);
    }

    @Test
    @Order(2)
    public void createHashKeyTest() {
        User user = this.userService.findUserByAccount(this.account);
        HashKey hashKey = this.hashService.createHashKeyFunc(
                HashKey.builder().hashName(this.hashKeyName).hashMain(0).build(),
                user.getUserId()
        );
        HashKey hashKey1 = this.hashService.searchHashKeyById(hashKey.getHashId(), user.getUserId());
        Assertions.assertNotNull(hashKey1);
    }

    @Test
    @Order(3)
    public void updateHashKeyTest() {
        User user = this.userService.findUserByAccount(this.account);
        String hashKeyNameEdit = "test-hash-key-edit";

        HashKey hashKey = this.hashService.searchHashKeyByHashName(hashKeyName, user.getUserId());
        Assertions.assertNotEquals(hashKey.getHashName(), hashKeyNameEdit);
        hashKey.setHashName(hashKeyNameEdit);
        HashKey editHashKey = this.hashService.updateHashKeyFunc(
                hashKey,
                user.getUserId()
        );
        Assertions.assertEquals(hashKeyNameEdit, editHashKey.getHashName());
    }
}
