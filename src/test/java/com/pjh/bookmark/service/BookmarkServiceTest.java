package com.pjh.bookmark.service;

import com.pjh.bookmark.common.StatusCode;
import com.pjh.bookmark.dto.UserRequestDto;
import com.pjh.bookmark.entity.Bookmark;
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
public class BookmarkServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BookmarkService bookmarkService;

    private final String account = "sign-up-bookmark-account";
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    @Order(1)
    public void signUp () {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .account(this.account)
                .f_password("1234")
                .s_password("1234")
                .name("sign up test")
                .build();

        logger.info(userRequestDto.toString());
        User singupResult = this.userService.signUp(userRequestDto);
        logger.info(singupResult.toString());
    }

    @Test
    @Order(2)
    public void createBookmarkTest() {
        logger.info("----------------------create bookamrk test--------------------------");
        User user = this.userService.findUserByAccount(this.account);
        Bookmark bookmark = this.bookmarkService.createBookmarkFunc(
                Bookmark.builder()
                .bookmarkName("bookamrk test")
                .bookmarkInfo("bookmark test info")
                .url("http://www.google.com")
                .build(),
                user.getUserId()
        );
        logger.info(bookmark.toString());
        Assertions.assertNotNull(bookmark);
    }

    @Test
    @Order(3)
    public void searchBookmarkTest() {
        logger.info("----------------------search bookamrk test--------------------------");
        User user = this.userService.findUserByAccount(this.account);
        Bookmark bookmark = this.bookmarkService.searchBookmarkByIdAndUserId(1, user.getUserId());
        logger.info(bookmark.toString());
        Assertions.assertNotNull(bookmark);

        this.bookmarkService.createBookmarkFunc(
                Bookmark.builder()
                        .bookmarkName("bookamrk test2")
                        .bookmarkInfo("bookmark test info2")
                        .url("http://www.google.com")
                        .build(),
                user.getUserId()
        );

        List<Bookmark> totalBookmarkList = this.bookmarkService.totalBookmarkListFunc(user.getUserId());
        logger.info("total bookmark size: " + totalBookmarkList.size());
        logger.info(totalBookmarkList.toString());
        Assertions.assertNotNull(totalBookmarkList);
        Assertions.assertEquals(totalBookmarkList.size(), 2);

        this.bookmarkService.createBookmarkFunc(
                Bookmark.builder()
                        .bookmarkName("bookamrk test2")
                        .bookmarkInfo("bookmark test info2")
                        .url("http://www.google.com")
                        .isMain(StatusCode.DEFAULT_BOOKMARK_NUM.getState())
                        .build(),
                user.getUserId()
        );

        List<Bookmark> mainBookmarkList = this.bookmarkService.mainBookmarkListFunc(user.getUserId());
        logger.info("main bookmark size: " + mainBookmarkList.size());
        logger.info(mainBookmarkList.toString());
        Assertions.assertNotNull(mainBookmarkList);
        Assertions.assertEquals(mainBookmarkList.size(), 1);
    }

    @Test
    @Order(4)
    public void addBookmarkFreqTest() {
        logger.info("----------------------add freq bookamrk test--------------------------");
        User user = this.userService.findUserByAccount(this.account);
        String result = this.bookmarkService.addBookmarkFrequencyFunc(1, user.getUserId());
        Assertions.assertEquals(result, "OK");
        Bookmark bookmark = this.bookmarkService.searchBookmarkByIdAndUserId(1, user.getUserId());
        Assertions.assertEquals(bookmark.getFrequency(), 1);
    }

    // TODO: 테스트 개발 필요
//    @Test
//    @Order(5)
//    public void bookmarkListNotHashMapTest() {
//
//    }

    @Test
    @Order(5)
    public void updateBookmarkTest() {
        logger.info("----------------------bookamrk update test--------------------------");
        User user = this.userService.findUserByAccount(this.account);

        long bookmarkId = 1;
        String bookmarkEditName = "bookamrk edit name";
        String bookmarkEditInfo = "bookmark edit info";
        String bookmarkEditUrl = "bookmark edit url";

        // bookmark name
        Bookmark bookmark = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, user.getUserId());
        bookmark.setBookmarkName(bookmarkEditName);
        this.bookmarkService.updateBookmarkFunc(bookmark, user.getUserId());
        Bookmark checkBookmark1 = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, user.getUserId());
        Assertions.assertEquals(checkBookmark1.getBookmarkName(), bookmarkEditName);
        Assertions.assertNotEquals(checkBookmark1.getBookmarkInfo(), bookmarkEditInfo);
        Assertions.assertNotEquals(checkBookmark1.getUrl(), bookmarkEditUrl);

        // bookmark info
        Bookmark bookmark2 = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, user.getUserId());
        bookmark2.setBookmarkInfo(bookmarkEditInfo);
        this.bookmarkService.updateBookmarkFunc(bookmark2, user.getUserId());
        Bookmark checkBookmark2 = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, user.getUserId());
        Assertions.assertEquals(checkBookmark2.getBookmarkName(), bookmarkEditName);
        Assertions.assertEquals(checkBookmark2.getBookmarkInfo(), bookmarkEditInfo);
        Assertions.assertNotEquals(checkBookmark2.getUrl(), bookmarkEditUrl);

        // bookmark url
        Bookmark bookmark3 = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, user.getUserId());
        bookmark3.setUrl(bookmarkEditUrl);
        this.bookmarkService.updateBookmarkFunc(bookmark3, user.getUserId());
        Bookmark checkBookmark3 = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, user.getUserId());
        Assertions.assertEquals(checkBookmark3.getBookmarkName(), bookmarkEditName);
        Assertions.assertEquals(checkBookmark3.getBookmarkInfo(), bookmarkEditInfo);
        Assertions.assertEquals(checkBookmark3.getUrl(), bookmarkEditUrl);
    }

    @Test
    @Order(7)
    public void searchBookmarkListByNameTest() {
        logger.info("----------------------bookamrk delete test--------------------------");
        User user = this.userService.findUserByAccount(this.account);
        String searchText1 = "test";
        List<Bookmark> bookmarkList1 = this.bookmarkService.searchBookmarkListByName(searchText1, user.getUserId());
        logger.info("bookmark search list (" + searchText1 + "): " + bookmarkList1.size());
        logger.info(bookmarkList1.toString());

        String searchText2 = "not";
        List<Bookmark> bookmarkList2 = this.bookmarkService.searchBookmarkListByName(searchText2, user.getUserId());
        logger.info("bookmark search list (" + searchText2 + "): " + bookmarkList2.size());
        logger.info(bookmarkList2.toString());

        String searchText3 = "";
        List<Bookmark> bookmarkList3 = this.bookmarkService.searchBookmarkListByName(searchText3, user.getUserId());
        logger.info("bookmark search list (" + searchText3 + "): " + bookmarkList3.size());
        logger.info(bookmarkList3.toString());
    }

    @Test
    @Order(8)
    public void deleteBookmarkTest() {
        logger.info("----------------------bookamrk delete test--------------------------");
        User user = this.userService.findUserByAccount(this.account);
        long bookmarkId = 1;

        Bookmark resultBookmark = this.bookmarkService.deleteBookmarkFunc(bookmarkId, user.getUserId());
        Assertions.assertEquals(resultBookmark.getState(), StatusCode.DEACTIVE_BOOKMARK_STATE.getState());
        try {
            Bookmark bookmark = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, user.getUserId());
        } catch (RuntimeException runtime) {
            logger.info("bookmark is not found: test success");
        }
    }
}
