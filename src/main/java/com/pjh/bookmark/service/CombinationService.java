package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombinationService {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HashService hashService;

    public CombinationResponseDto bookmarkAndHashSave(CombinationRequestDto combinationRequestDto, long userId) {

        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto();
        bookmarkRequestDto.setBookmark(combinationRequestDto.getBookmark());
        BookmarkResponseDto bookmarkResponseDto = bookmarkService.createBookmarkFunc(bookmarkRequestDto, userId);

        HashRequestDto hashRequestDto = new HashRequestDto();
        hashRequestDto.setHashKeyList(combinationRequestDto.getHashKeyList());
        hashRequestDto.setBookmarkId(bookmarkResponseDto.getBookmarkList().get(0).getBookmarkId());
        HashResponseDto hashResponseDto = hashService.createHashMapAndBookmarkFunc(hashRequestDto,userId);

        return new CombinationResponseDto(bookmarkResponseDto.getBookmarkList(), hashResponseDto.getHashKeyList());
    }
}
