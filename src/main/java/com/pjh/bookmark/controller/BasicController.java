package com.pjh.bookmark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {

    @GetMapping("/error")
    public String errorToVuePage() {
        return "error";
    }
}
