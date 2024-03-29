package com.pjh.bookmark.controller;

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

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HashService hashService;

    @Autowired
    private AuthService authService;

    @GetMapping(path="", consumes = "*/*", produces = "application/json")
    public ResponseEntity<List<Bookmark>> getTotalBookmarkListApi(@RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.totalBookmarkListFunc(authService.tokenDecode(token)));
    }

    @GetMapping(path="/main", consumes = "*/*", produces = "application/json")
    public ResponseEntity<List<Bookmark>> getMainBookmarkListApi(@RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.mainBookmarkListFunc(authService.tokenDecode(token)));
    }

    @GetMapping(path="/search", consumes = "*/*", produces = "application/json")
    public ResponseEntity<List<Bookmark>> getSearchBookmarkListApi(@RequestParam("bookmark") String bookmarkName, @RequestHeader("auth_token") String token) {
        return ResponseEntity.ok().body(bookmarkService.searchBookmarkListByName(bookmarkName, authService.tokenDecode(token)));
    }

//    @GetMapping(path="/hash/{hashId}", consumes = "*/*", produces = "application/json")
//    public ResponseEntity<List<Bookmark>> getBookmarkListByHashIdApi(@PathVariable("hashId") long hashId){
//        return ResponseEntity.ok().body(hashService.bookmarkListByHashKeyFunc(hashId));
//    }

    @GetMapping(path="/unmapped", consumes = "*/*", produces = "application/json")
    public ResponseEntity<List<Bookmark>> getBookmarkListNotHashMapApi(@RequestHeader("auth_token") String token) {
        return ResponseEntity.ok().body(bookmarkService.bookmarkListNotHashMapFunc(authService.tokenDecode(token)));
    }

    @GetMapping(path = "/{bookmarkId}/freq", consumes = "*/*", produces = "*/*")
    public ResponseEntity<String> addFrequencyByBookmarkIdApi(@PathVariable("bookmarkId") long bookmarkId, @RequestHeader("auth_token") String token){
        return  ResponseEntity.ok().body(bookmarkService.addBookmarkFrequencyFunc(bookmarkId, authService.tokenDecode(token)));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Bookmark> postCreateNewBookmarkApi(@RequestBody @Valid Bookmark bookmark, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.createBookmarkFunc(bookmark, authService.tokenDecode(token)));
    }

    @PatchMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Bookmark> patchBookmarkApi(@RequestBody @Valid Bookmark bookmark, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.updateBookmarkFunc(bookmark, authService.tokenDecode(token)));
    }

    @DeleteMapping(path="/{bookmarkId}", produces = "application/json")
    public ResponseEntity<Bookmark> deleteBookmarkApi(@PathVariable("bookmarkId") long bookmarkId, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(bookmarkService.deleteBookmarkFunc(bookmarkId, authService.tokenDecode(token)));
    }

}
