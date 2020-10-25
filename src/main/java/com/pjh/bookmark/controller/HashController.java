package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.HashRequestDto;
import com.pjh.bookmark.dto.HashResponseDto;
import com.pjh.bookmark.service.AuthService;
import com.pjh.bookmark.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hash")
public class HashController {

    @Autowired
    private HashService hashService;

    @Autowired
    private AuthService authService;

    @GetMapping(path="/bookmark", produces = "application/json")
    public HashResponseDto getTotalHashKeyListByBookmarkIdApi(@RequestParam("bookmarkId") long bookmarkId){
        return hashService.hashKeyListByBookmarkFunc(bookmarkId);
    }

    @GetMapping(path="/user", produces = "application/json")
    public HashResponseDto getTotalHashKeyListByUserIdApi(@RequestHeader("auth_token") String token){
        return hashService.hashKeyListByUserFunc(authService.tokenDecode(token));
    }

    @GetMapping(path="/main", produces = "application/json")
    public HashResponseDto getMainHashKeyListByUser(@RequestHeader("auth_token") String token){
        return hashService.mainHashKeyListFunc(authService.tokenDecode(token));
    }

    @PostMapping(path="/map", consumes = "application/json", produces = "application/json")
    public HashResponseDto postMappingHashWithBookmarkApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return hashService.createHashMapAndBookmarkFunc(hashRequestDto,authService.tokenDecode(token));
    }

    @PatchMapping(path="/map", consumes = "application/json", produces = "application/json")
    public HashResponseDto patchMappingHashWithBookmarkApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return hashService.editMappingHashAndBookmark(hashRequestDto,authService.tokenDecode(token));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public HashResponseDto postCreateNewHashKeyApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return hashService.createHashKeyFunc(hashRequestDto,authService.tokenDecode(token));
    }

    @PatchMapping(path="", consumes = "application/json", produces = "application/json")
    public HashResponseDto patchHashKeyApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return hashService.updateHashKeyFunc(hashRequestDto,authService.tokenDecode(token));
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity deleteHashKeyApi(@RequestBody HashRequestDto hashRequestDto){
        return hashService.deleteHashMapByHashKeyFunc(hashRequestDto);
    }
}
