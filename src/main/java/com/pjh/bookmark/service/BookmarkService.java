package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.HashKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookmarkService {

    static final int NORMAL_STATIC = 1;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private HashService hashService;

    public BookmarkResponseDto selectAll(long userId){
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        bookmarkResponseDto.setBookmarkList(bookmarkRepository.findByUserIdAndState(userId, NORMAL_STATIC));
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto selectMain(long userId){
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        bookmarkResponseDto.setBookmarkList(bookmarkRepository.findByUserIdAndIsMainAndState(userId,1,1));
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto insertNew(BookmarkRequestDto bookmarkRequestDto){

        List<Bookmark> bookmarks = new ArrayList<>();
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        for (Bookmark indiBookmark : bookmarkRequestDto.getBookmarkList()){
            bookmarks.add(indiBookmark);
        }
        bookmarkRepository.saveAll(bookmarks);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto update(BookmarkRequestDto bookmarkRequestDto){
        //TODO: 하나만 업데이트 할 수 있도록 수정 필요
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        List<Bookmark> bookmarks = new ArrayList<>();
        for ( Bookmark indiBookmark : bookmarkRequestDto.getBookmarkList()){
            indiBookmark.setState(1);
            bookmarks.add(indiBookmark);
        }
        bookmarkRepository.saveAll(bookmarks);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto delete(BookmarkRequestDto bookmarkRequestDto){
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        List<Bookmark> bookmarks = new ArrayList<>();
        for ( Bookmark indiBookmark : bookmarkRequestDto.getBookmarkList()){
            Bookmark bookmark = bookmarkRepository.findByBookmarkId(indiBookmark.getBookmarkId());
            hashService.deleteMappingHashAndBookamrkByBookmarkDeleted(bookmark.getBookmarkId());
            bookmark.setState(0);
            bookmarks.add(bookmark);
        }
        bookmarkRepository.saveAll(bookmarks);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }
}
