package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Tag;

import java.util.List;

public class TagResponseDto {
    public List<Tag> tagList;

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
}
