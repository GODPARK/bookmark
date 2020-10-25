package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;

import java.util.List;

public class CombinationResponseDto {
    private List<Bookmark> bookmarkList;
    private List<HashKey> hashKeyList;

    public CombinationResponseDto() {}
    public CombinationResponseDto(List<Bookmark> bookmarkList, List<HashKey> hashKeyList) {
        this.bookmarkList = bookmarkList;
        this.hashKeyList = hashKeyList;
    }

    public List<HashKey> getHashKeyList() {
        return hashKeyList;
    }

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }

    public void setHashKeyList(List<HashKey> hashKeyList) {
        this.hashKeyList = hashKeyList;
    }

    public void setBookmarkList(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }
}
