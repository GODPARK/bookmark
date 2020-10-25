package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.HashKey;

import java.util.ArrayList;
import java.util.List;

public class HashResponseDto {
    private List<HashKey> hashKeyList;
    public HashResponseDto() {}
    public HashResponseDto(HashKey hashKey) {
        if(hashKey != null) {
            this.hashKeyList = new ArrayList<>();
            this.hashKeyList.add(hashKey);
        }
    }
    public HashResponseDto(List<HashKey> hashKeyList) {
        this.hashKeyList = hashKeyList;
    }
    public List<HashKey> getHashKeyList() {
        return hashKeyList;
    }

    public void setHashKeyList(List<HashKey> hashKeyList) {
        this.hashKeyList = hashKeyList;
    }
}
