package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.WorkResponseDto;
import com.pjh.bookmark.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/work")
public class WorkController {
    @Autowired
    private WorkService workService;

    @GetMapping(path="/total", consumes = "*/*", produces = "application/json")
    public WorkResponseDto getTotalWorkListApi() {
        return workService.totalWorkListFunc();
    }

    @GetMapping(path="", consumes = "*/*", produces = "application/json")
    public void getWorkByWorkIdApi(@PathParam(value = "workId") long workId, @RequestHeader("auth_token") String token) {

    }

    @PostMapping(path="", consumes = "*/*", produces = "application/json")
    public void postCreateNewWorkApi() {

    }

    @PatchMapping(path="", consumes = "*/*", produces = "application/json")
    public void patchWorkApi(@RequestHeader("auth_token") String token) {

    }

    @DeleteMapping(path="", consumes = "*/*", produces = "application/json")
    public void deleteWorkApi(@RequestHeader("auth_token") String token) {

    }
}
