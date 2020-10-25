package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;
import org.springframework.security.core.parameters.P;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class BookmarkResponseDto {
    private List<Bookmark> bookmarkList;

    public BookmarkResponseDto() {}
    public BookmarkResponseDto(Bookmark bookmark) {
        if (bookmark != null) {
            this.bookmarkList = new ArrayList<>();
            this.bookmarkList.add(bookmark);
        }
    }
    public BookmarkResponseDto(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }
    public void setBookmarkList(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }
}
