package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.HashKey;

import java.util.List;

public class HashResponseDto {
    private List<HashKey> hashKeyList;

    public List<HashKey> getHashKeyList() {
        return hashKeyList;
    }

    public void setHashKeyList(List<HashKey> hashKeyList) {
        this.hashKeyList = hashKeyList;
    }
}
