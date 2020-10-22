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
    public HashResponseDto getAllHashByBookmark(@RequestParam("bookmarkId") long bookmarkId){
        return hashService.selectByBookmark(bookmarkId);
    }

    @GetMapping(path="/user", produces = "application/json")
    public HashResponseDto getAllHashByUser(@RequestHeader("auth_token") String token){
        return hashService.selectByUser(authService.tokenDecode(token));
    }

    @GetMapping(path="/main", produces = "application/json")
    public HashResponseDto getMainHashByUser(@RequestHeader("auth_token") String token){
        return hashService.mainHashListByUserId(authService.tokenDecode(token));
    }

    @PostMapping(path="/map", consumes = "application/json", produces = "application/json")
    public HashResponseDto mappingHash(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return hashService.saveMappingHashAndBookmark(hashRequestDto,authService.tokenDecode(token));
    }

    @PatchMapping(path="/map", consumes = "application/json", produces = "application/json")
    public HashResponseDto editMappingHash(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return hashService.editMappingHashAndBookmark(hashRequestDto,authService.tokenDecode(token));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public HashResponseDto saveHash(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return hashService.saveHashKey(hashRequestDto,authService.tokenDecode(token));
    }

    @PutMapping(path="", consumes = "application/json", produces = "application/json")
    public HashResponseDto updateHash(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return hashService.updateHashKey(hashRequestDto,authService.tokenDecode(token));
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity deleteHash(@RequestBody HashRequestDto hashRequestDto){
        return hashService.deleteMappingHashAndBookmarkByHashKeyDeleted(hashRequestDto);
    }
}
