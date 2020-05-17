package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.HashKey;

import java.util.List;

public class HashRequestDto {

    private List<HashKey> hashKeyList;
    private long bookmarkId;

    public long getBookmarkId() {
        return bookmarkId;
    }

    public List<HashKey> getHashKeyList() {
        return hashKeyList;
    }

    public void setBookmarkId(long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public void setHashKeyList(List<HashKey> hashKeyList) {
        this.hashKeyList = hashKeyList;
    }
}
