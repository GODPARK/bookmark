package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;

import java.util.List;

public class BookmarkRequestDto {
    private Bookmark bookmark;

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
