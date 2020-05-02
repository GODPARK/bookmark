package com.pjh.bookmark.controller;

import com.pjh.bookmark.dto.TagRequestDto;
import com.pjh.bookmark.dto.TagResponseDto;
import com.pjh.bookmark.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(path="", consumes = "application/json", produces = "application/json")
    public TagResponseDto getAllTagByUser(@RequestBody TagRequestDto tagRequestDto){
        return tagService.selectAll(tagRequestDto);
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public TagResponseDto saveNewTagByUser(@RequestBody TagRequestDto tagRequestDto){
        return tagService.insertNew(tagRequestDto);
    }

    @PutMapping(path="", consumes = "application/json", produces = "application/json")
    public TagResponseDto updateTagByUser(@RequestBody TagRequestDto tagRequestDto){
        return tagService.update(tagRequestDto);
    }

    @DeleteMapping(path="", consumes = "application/json", produces = "application/json")
    public TagResponseDto deleteTagByUser(@RequestBody TagRequestDto tagRequestDto){
        return  tagService.delete(tagRequestDto);
    }
}
