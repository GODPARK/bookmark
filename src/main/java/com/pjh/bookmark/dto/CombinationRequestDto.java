package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;

import java.util.List;

public class CombinationRequestDto {
    private Bookmark bookmark;
    private List<HashKey> hashKeyList;

    public List<HashKey> getHashKeyList() {
        return hashKeyList;
    }

    public void setHashKeyList(List<HashKey> hashKeyList) {
        this.hashKeyList = hashKeyList;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }
}
