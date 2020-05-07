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

    public TagResponseDto selectAll(long userId){
        TagResponseDto tagResponseDto = new TagResponseDto();
        tagResponseDto.setTagList(tagRepository.findByUserIdAndState(userId,NORMAL_STATIC));
        return tagResponseDto;
    }

    public TagResponseDto insertNew(TagRequestDto tagRequestDto){
        TagResponseDto tagResponseDto = new TagResponseDto();
        List<Tag> tags = new ArrayList<>();

        for ( Tag indiTag : tagRequestDto.getTag()) {
            indiTag.setState(1);
            tags.add(indiTag);
        }
        tagRepository.saveAll(tags);
        tagResponseDto.setTagList(tags);
        return tagResponseDto;
    }

    public TagResponseDto update(TagRequestDto tagRequestDto){
        TagResponseDto tagResponseDto = new TagResponseDto();
        List<Tag> tags = new ArrayList<>();
        for ( Tag indiTag : tagRequestDto.getTag()) {
            indiTag.setState(1);
            tags.add(indiTag);
        }
        tagRepository.saveAll(tags);
        tagResponseDto.setTagList(tags);
        return tagResponseDto;
    }

    public TagResponseDto delete(TagRequestDto tagRequestDto){
        TagResponseDto tagResponseDto = new TagResponseDto();
        List<Tag> tags = new ArrayList<>();

        for ( Tag indiTag : tagRequestDto.getTag() ) {
            Tag tag = tagRepository.findByTagId(indiTag.getTagId());
            tag.setState(0);
            tags.add(tag);
        }
        tagRepository.saveAll(tags);
        tagResponseDto.setTagList(tags);
        return tagResponseDto;
    }
}
