package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;

import java.util.List;

public class BookmarkRequestDto {
    public List<Bookmark> bookmark;

    public List<Bookmark> getBookmark() {
        return bookmark;
    }

    public void setBookmark(List<Bookmark> bookmark) {
        this.bookmark = bookmark;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
