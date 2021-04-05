package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.HashRequestDto;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.service.AuthService;
import com.pjh.bookmark.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hash")
public class HashController {

    @Autowired
    private HashService hashService;

    @Autowired
    private AuthService authService;

    @GetMapping(path="/bookmark/{bookmarkId}", produces = "application/json")
    public ResponseEntity<List<HashKey>> getTotalHashKeyListByBookmarkIdApi(@PathVariable("bookmarkId") long bookmarkId){
        return ResponseEntity.ok().body(hashService.hashKeyListByBookmarkFunc(bookmarkId));
    }

    @GetMapping(path="/user", produces = "application/json")
    public ResponseEntity<List<HashKey>> getTotalHashKeyListByUserIdApi(@RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.hashKeyListByUserFunc(authService.tokenDecode(token)));
    }

    @GetMapping(path="/main", produces = "application/json")
    public ResponseEntity<List<HashKey>> getMainHashKeyListByUser(@RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.mainHashKeyListFunc(authService.tokenDecode(token)));
    }

    @PostMapping(path="/map", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<HashKey>> postMappingHashWithBookmarkApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.createHashMapAndBookmarkFunc(hashRequestDto,authService.tokenDecode(token)));
    }

    @PatchMapping(path="/map", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<HashKey>> patchMappingHashWithBookmarkApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.editMappingHashAndBookmark(hashRequestDto,authService.tokenDecode(token)));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<HashKey>> postCreateNewHashKeyApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.createHashKeyFunc(hashRequestDto,authService.tokenDecode(token)));
    }

    @PatchMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<HashKey>> patchHashKeyApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.updateHashKeyFunc(hashRequestDto,authService.tokenDecode(token)));
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity deleteHashKeyApi(@RequestBody HashRequestDto hashRequestDto){
        return hashService.deleteHashMapByHashKeyFunc(hashRequestDto);
    }
}
