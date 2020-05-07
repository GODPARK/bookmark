package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Tag;

import java.util.List;

public class TagRequestDto {
    public List<Tag> tag;

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }
}
