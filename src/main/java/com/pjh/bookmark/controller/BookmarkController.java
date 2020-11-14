package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.CombinationRequestDto;
import com.pjh.bookmark.dto.CombinationResponseDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.service.AuthService;
import com.pjh.bookmark.service.BookmarkService;
import com.pjh.bookmark.service.CombinationService;
import com.pjh.bookmark.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/bmk/api/bookmark")
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
    public ResponseEntity<List<Bookmark>> getTotalBookmarkListApi(@RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.totalBookmarkListFunc(authService.tokenDecode(token)));
    }

    @GetMapping(path="/main", consumes = "*/*", produces = "application/json")
    public ResponseEntity<List<Bookmark>> getMainBookmarkListApi(@RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.mainBookmarkListFunc(authService.tokenDecode(token)));
    }

    @GetMapping(path="/hash", consumes = "*/*", produces = "application/json")
    public ResponseEntity<List<Bookmark>> getBookmarkListByHashIdApi(@PathParam(value="hashId") long hashId){
        return ResponseEntity.ok().body(hashService.bookmarkListByHashKeyFunc(hashId));
    }

    @GetMapping(path="/unmapped", consumes = "*/*", produces = "application/json")
    public ResponseEntity<List<Bookmark>> getBookmarkListNotHashMapApi(@RequestHeader("auth_token") String token) {
        return ResponseEntity.ok().body(bookmarkService.bookmarkListNotHashMapFunc(authService.tokenDecode(token)));
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
    public ResponseEntity<Bookmark> patchBookmarkApi(@RequestBody BookmarkRequestDto bookmarkRequestDto, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.updateBookmarkFunc(bookmarkRequestDto, authService.tokenDecode(token)));
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Bookmark> deleteBookmarkApi(@RequestBody BookmarkRequestDto bookmarkRequestDto, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.deleteBookmarkFunc(bookmarkRequestDto, authService.tokenDecode(token)));
    }

}
