package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;

import java.util.List;

public class BookmarkRequestDto {
    public List<Bookmark> bookmarkList;

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }

    public void setBookmarkList(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
