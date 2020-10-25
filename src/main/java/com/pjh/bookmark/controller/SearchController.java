package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.SearchRequestDto;
import com.pjh.bookmark.service.AuthService;
import com.pjh.bookmark.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/record", consumes = "application/json", produces = "*/*")
    public void postCreateSearchByUserApi(@RequestBody SearchRequestDto searchRequestDto, @RequestHeader("auth_token") String token) {
        searchService.saveSearchRecord(searchRequestDto, authService.tokenDecode(token));
    }
}
