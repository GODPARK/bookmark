package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.service.BookmarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping(path="", consumes = "*/*", produces = "application/json")
    public BookmarkResponseDto getAllBookmarkByUser(@PathParam(value="userId") long userId){
        return bookmarkService.selectAll(userId);
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto saveNewBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto){
        return bookmarkService.insertNew(bookmarkRequestDto);
    }

    @PatchMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto updateBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto){
        return bookmarkService.update(bookmarkRequestDto);
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto deleteBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto){
        return bookmarkService.delete(bookmarkRequestDto);
    }

}
