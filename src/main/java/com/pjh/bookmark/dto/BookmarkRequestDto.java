package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;

import java.util.List;

public class BookmarkRequestDto {
    public Bookmark bookmark;

    public Bookmark getBookmark() {
        return bookmark;
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }
}
