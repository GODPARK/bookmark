package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.HashRequestDto;
import com.pjh.bookmark.dto.HashResponseDto;
import com.pjh.bookmark.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hash")
public class HashController {

    @Autowired
    private HashService hashService;

    @GetMapping(path="", produces = "application/json")
    public HashResponseDto getAllHashByBookmark(@RequestParam("bookmarkId") long bookmarkId){
        return hashService.selectAll(bookmarkId);
    }

    @PostMapping(path="/save", consumes = "application/json", produces = "application/json")
    public HashResponseDto saveHash(@RequestBody HashRequestDto hashRequestDto){
        return hashService.saveMappingHashAndBookmark(hashRequestDto);
    }

    @DeleteMapping(path="/delete", consumes = "application/json", produces = "application/json")
    public HashResponseDto deleteHash(@RequestBody HashRequestDto hashRequestDto){
        return hashService.deleteMappingHash(hashRequestDto);
    }
}
