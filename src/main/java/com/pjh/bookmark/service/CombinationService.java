package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.*;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CombinationService {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HashService hashService;

    public CombinationResponseDto bookmarkAndHashSave(CombinationRequestDto combinationRequestDto, long userId) {

        List<Bookmark> bookmarkList = new ArrayList<>();
        bookmarkList.add(bookmarkService.createBookmarkFunc(combinationRequestDto.getBookmark(), userId));

        HashRequestDto hashRequestDto = new HashRequestDto();
        hashRequestDto.setHashKeyList(combinationRequestDto.getHashKeyList());
        hashRequestDto.setBookmarkId(bookmarkList.get(0).getBookmarkId());
        List<HashKey> hashKeyList = hashService.createHashMapAndBookmarkFunc(hashRequestDto, userId);

        return new CombinationResponseDto(bookmarkList, hashKeyList);
    }
}
