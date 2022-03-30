package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.HashRequestDto;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.service.AuthService;
import com.pjh.bookmark.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping(path="/map/bookmark/{bookmarkId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<HashKey>> postMappingHashWithBookmarkApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token, @PathVariable("bookmarkId") long bookmarkId){
        return ResponseEntity.ok().body(hashService.createHashMapAndBookmarkFunc(hashRequestDto.getHashKeyList(), authService.tokenDecode(token), bookmarkId));
    }

    @PatchMapping(path="/map/bookmark/{bookmarkId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<HashKey>> patchMappingHashWithBookmarkApi(@RequestBody HashRequestDto hashRequestDto, @RequestHeader("auth_token") String token, @PathVariable("bookmarkId") long bookmarkId){
        return ResponseEntity.ok().body(hashService.editMappingHashAndBookmark(hashRequestDto.getHashKeyList(), bookmarkId, authService.tokenDecode(token)));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<HashKey> postCreateNewHashKeyApi(@RequestBody @Valid HashKey hashKey, @RequestHeader("auth_token") String token){
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
