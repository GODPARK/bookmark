package com.pjh.bookmark.controller;

import com.pjh.bookmark.component.TokenEncoding;
import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.dto.CombinationRequestDto;
import com.pjh.bookmark.dto.CombinationResponseDto;
import com.pjh.bookmark.service.AuthService;
import com.pjh.bookmark.service.BookmarkService;
import com.pjh.bookmark.service.CombinationService;
import com.pjh.bookmark.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HashService hashService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CombinationService combinationService;

    @GetMapping(path="", consumes = "*/*", produces = "application/json")
    public BookmarkResponseDto getTotalBookmarkListApi(@RequestHeader("auth_token") String token){
        return bookmarkService.totalBookmarkListFunc(authService.tokenDecode(token));
    }

    @GetMapping(path="/main", consumes = "*/*", produces = "application/json")
    public BookmarkResponseDto getMainBookmarkListApi(@RequestHeader("auth_token") String token){
        return bookmarkService.mainBookmarkListFunc(authService.tokenDecode(token));
    }

    @GetMapping(path="/hash", consumes = "*/*", produces = "application/json")
    public BookmarkResponseDto getBookmarkListByHashIdApi(@PathParam(value="hashId") long hashId){
        return hashService.bookmarkListByHashKeyFunc(hashId);
    }

    @GetMapping(path="/unmapped", consumes = "*/*", produces = "application/json")
    public BookmarkResponseDto getBookmarkListNotHashMapApi(@RequestHeader("auth_token") String token) {
        return bookmarkService.bookmarkListNotHashMapFunc(authService.tokenDecode(token));
    }

    @GetMapping(path = "/freq", consumes = "*/*", produces = "*/*")
    public void addFrequencyByBookmarkIdApi(@PathParam(value = "bookmarkId") long bookmarkId, @RequestHeader("auth_token") String token){
        bookmarkService.addBookmarkFrequencyFunc(bookmarkId, authService.tokenDecode(token));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public CombinationResponseDto postCreateNewBookmarkApi(@RequestBody CombinationRequestDto combinationRequestDto, @RequestHeader("auth_token") String token){
        return combinationService.bookmarkAndHashSave(combinationRequestDto,authService.tokenDecode(token));
    }

    @PatchMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto patchBookmarkApi(@RequestBody BookmarkRequestDto bookmarkRequestDto, @RequestHeader("auth_token") String token){
        return bookmarkService.updateBookmarkFunc(bookmarkRequestDto, authService.tokenDecode(token));
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto deleteBookmarkApi(@RequestBody BookmarkRequestDto bookmarkRequestDto, @RequestHeader("auth_token") String token){
        return bookmarkService.deleteBookmarkFunc(bookmarkRequestDto, authService.tokenDecode(token));
    }

}
