package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.TagRequestDto;
import com.pjh.bookmark.dto.TagResponseDto;
import com.pjh.bookmark.entity.Tag;
import com.pjh.bookmark.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    static final int NORMAL_STATIC = 1;

    @Autowired
    private TagRepository tagRepository;

    public TagResponseDto selectAll(TagRequestDto tagRequestDto){
        TagResponseDto tagResponseDto = new TagResponseDto();
        tagResponseDto.setTagList(tagRepository.findByUserIdAndState(tagRequestDto.tag.getUserId(),NORMAL_STATIC));
        return tagResponseDto;
    }

    public TagResponseDto insertNew(TagRequestDto tagRequestDto){
        TagResponseDto tagResponseDto = new TagResponseDto();
        List<Tag> tags = new ArrayList<>();
        tags.add(tagRepository.save(tagRequestDto.tag));
        tagResponseDto.setTagList(tags);
        return tagResponseDto;
    }

    public TagResponseDto update(TagRequestDto tagRequestDto){
        TagResponseDto tagResponseDto = new TagResponseDto();
        List<Tag> tags = new ArrayList<>();
        tags.add(tagRepository.save(tagRequestDto.tag));
        tagResponseDto.setTagList(tags);
        return tagResponseDto;
    }

    public TagResponseDto delete(TagRequestDto tagRequestDto){
        TagResponseDto tagResponseDto = new TagResponseDto();
        List<Tag> tags = new ArrayList<>();

        Tag tag = tagRepository.findByTagId(tagRequestDto.tag.getTagId());
        tag.setState(0);
        tags.add(tagRepository.save(tag));
        tagResponseDto.setTagList(tags);
        return tagResponseDto;
    }
}
