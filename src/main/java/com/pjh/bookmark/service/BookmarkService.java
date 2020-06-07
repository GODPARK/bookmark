package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.exception.SuccessException;
import com.pjh.bookmark.exception.UnExpectedException;
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

    public void addBookmarkFrequency(long bookmarkId, long userId){
        Bookmark bookmark = bookmarkRepository.findByBookmarkIdAndUserId(bookmarkId, userId);
        if( bookmark != null){
            bookmark.setFrequency(bookmark.getFrequency() + 1);
            bookmarkRepository.save(bookmark);
            throw new SuccessException("freq add success");
        }
        else {
            throw new UnExpectedException("freq add fail");
        }
    }

    public BookmarkResponseDto insertNew(BookmarkRequestDto bookmarkRequestDto, long userId){

        List<Bookmark> bookmarks = new ArrayList<>();
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        for (Bookmark indiBookmark : bookmarkRequestDto.getBookmarkList()){
            indiBookmark.setUserId(userId);
            bookmarks.add(indiBookmark);
        }
        bookmarkRepository.saveAll(bookmarks);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto update(BookmarkRequestDto bookmarkRequestDto, long userId){
        //TODO: 하나만 업데이트 할 수 있도록 수정 필요
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        List<Bookmark> bookmarks = new ArrayList<>();
        for ( Bookmark indiBookmark : bookmarkRequestDto.getBookmarkList()){
            indiBookmark.setUserId(userId);
            indiBookmark.setState(1);
            bookmarks.add(indiBookmark);
        }
        bookmarkRepository.saveAll(bookmarks);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto delete(BookmarkRequestDto bookmarkRequestDto, long userId){
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        List<Bookmark> bookmarks = new ArrayList<>();
        for ( Bookmark indiBookmark : bookmarkRequestDto.getBookmarkList()){
            Bookmark bookmark = bookmarkRepository.findByBookmarkIdAndUserId(indiBookmark.getBookmarkId(), userId);
            hashService.deleteMappingHashAndBookamrkByBookmarkDeleted(bookmark.getBookmarkId());
            bookmark.setState(0);
            bookmark.setUserId(userId);
            bookmarks.add(bookmark);
        }
        bookmarkRepository.saveAll(bookmarks);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }
}
