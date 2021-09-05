package com.pjh.bookmark.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/statistics")
public class StatisticsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping(value = "/wake-up", consumes = "*/*", produces = "*/*")
    public ResponseEntity<String> collectFirstPage(HttpServletRequest httpServletRequest) {
        logger.info(httpServletRequest.getRemoteHost());
        return ResponseEntity.ok().body("collect");
    }
}
