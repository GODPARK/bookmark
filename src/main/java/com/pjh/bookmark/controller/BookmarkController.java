package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto getAllBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto){
        return bookmarkService.selectAll(bookmarkRequestDto);
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto saveNewBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto){
        return bookmarkService.insertNew(bookmarkRequestDto);
    }

    @PutMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto updateBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto){
        return bookmarkService.update(bookmarkRequestDto);
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public BookmarkResponseDto deleteBookmarkByUser(@RequestBody BookmarkRequestDto bookmarkRequestDto){
        return bookmarkService.delete(bookmarkRequestDto);
    }

}
