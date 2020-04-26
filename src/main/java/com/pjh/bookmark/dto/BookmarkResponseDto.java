package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;

import java.util.List;

public class BookmarkResponseDto {
    public List<Bookmark> bookmarkList;

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }

    public void setBookmarkList(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }
}
