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
    public ResponseEntity<List<HashKey>> getTotalHashKeyListByBookmarkIdApi(@PathVariable("bookmarkId") long bookmarkId, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.hashKeyListByBookmarkFunc(bookmarkId, authService.tokenDecode(token)));
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
        return ResponseEntity.ok().body(hashService.createHashMapAndBookmarkFunc(hashRequestDto, authService.tokenDecode(token)));
    }

    @PatchMapping(path="/map", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<HashKey>> patchMappingHashWithBookmarkApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.editMappingHashAndBookmark(hashRequestDto,authService.tokenDecode(token)));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<HashKey> postCreateNewHashKeyApi(@RequestBody HashKey hashKey, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.createHashKeyFunc(hashKey, authService.tokenDecode(token)));
    }

    @PatchMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<HashKey> patchHashKeyApi(@RequestBody HashKey hashKey, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.updateHashKeyFunc(hashKey, authService.tokenDecode(token)));
    }

    @DeleteMapping(path="/{hashId}", produces = "application/json")
    public ResponseEntity<HashKey> deleteHashKeyApi(@PathVariable("hashId") long hashId, @RequestHeader("auth_token") String token){
        return ResponseEntity.ok().body(hashService.deleteHashMapAndHashKeyFunc(hashId, authService.tokenDecode(token)));
    }
}
