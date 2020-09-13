package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombinationService {

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    HashService hashService;

    public CombinationResponseDto bookmarkAndHashSave(CombinationRequestDto combinationRequestDto, long userId) {

        CombinationResponseDto combinationResponseDto = new CombinationResponseDto();

        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto();
        bookmarkRequestDto.setBookmark(combinationRequestDto.getBookmark());
        BookmarkResponseDto bookmarkResponseDto = bookmarkService.insertNew(bookmarkRequestDto, userId);

        HashRequestDto hashRequestDto = new HashRequestDto();
        hashRequestDto.setHashKeyList(combinationRequestDto.getHashKeyList());
        hashRequestDto.setBookmarkId(bookmarkResponseDto.getBookmarkList().get(0).getBookmarkId());
        HashResponseDto hashResponseDto = hashService.saveMappingHashAndBookmark(hashRequestDto,userId);

        combinationResponseDto.setBookmarkList(bookmarkResponseDto.getBookmarkList());
        combinationRequestDto.setHashKeyList(hashResponseDto.getHashKeyList());
        return combinationResponseDto;
    }
}
