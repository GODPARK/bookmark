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
    public BookmarkResponseDto getAllBookmarkByUser(@RequestHeader("auth_token") String token){
        return bookmarkService.selectAll(authService.tokenDecode(token));
    }

    @GetMapping(path="/main", consumes = "*/*", produces = "application/json")
    public BookmarkResponseDto getMainBookmarkByUser(@RequestHeader("auth_token") String token){
        return bookmarkService.selectMain(authService.tokenDecode(token));
    }

    @GetMapping(path="/hash", consumes = "*/*", produces = "application/json")
    public BookmarkResponseDto getBookmarkByHash(@PathParam(value="hashId") long hashId){
        return hashService.selectByHashId(hashId);
    }

    @GetMapping(path = "/freq", consumes = "*/*", produces = "*/*")
    public void addFrequencyBookmark(@PathParam(value = "bookmarkId") long bookmarkId, @RequestHeader("auth_token") String token){
        bookmarkService.addBookmarkFrequency(bookmarkId, authService.tokenDecode(token));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public CombinationResponseDto saveNewBookmarkByUser(@RequestBody CombinationRequestDto combinationRequestDto, @RequestHeader("auth_token") String token){
        return combinationService.bookmarkAndHashSave(combinationRequestDto,authService.tokenDecode(token));
    }

    @PatchMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto updateBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto, @RequestHeader("auth_token") String token){
        return bookmarkService.update(bookmarkRequestDto, authService.tokenDecode(token));
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto deleteBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto, @RequestHeader("auth_token") String token){
        return bookmarkService.delete(bookmarkRequestDto, authService.tokenDecode(token));
    }

}
