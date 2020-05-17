package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.HashKey;

import java.util.List;

public class HashResponseDto {
    List<HashKey> hashKeyList;

    public List<HashKey> getHashKeyList() {
        return hashKeyList;
    }

    public void setHashKeyList(List<HashKey> hashKeyList) {
        this.hashKeyList = hashKeyList;
    }

    public void addHashKey(HashKey hashKey){
        this.hashKeyList.add(hashKey);
    }
}
