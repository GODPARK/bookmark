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

import java.awt.print.Book;
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
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        List<Bookmark> bookmarks = new ArrayList<>();

        Bookmark saveBookmark = bookmarkRequestDto.getBookmark();
        saveBookmark.setUserId(userId);

        bookmarkRepository.save(saveBookmark);
        bookmarks.add(saveBookmark);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto update(BookmarkRequestDto bookmarkRequestDto, long userId){
        //TODO: 하나만 업데이트 할 수 있도록 수정 필요
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        List<Bookmark> bookmarks = new ArrayList<>();

        Bookmark updateBookmark = bookmarkRequestDto.getBookmark();
        updateBookmark.setUserId(userId);
        updateBookmark.setState(1);

        bookmarkRepository.save(updateBookmark);
        bookmarks.add(updateBookmark);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto delete(BookmarkRequestDto bookmarkRequestDto, long userId){
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        List<Bookmark> bookmarks = new ArrayList<>();

        Bookmark deleteBookmark = bookmarkRequestDto.getBookmark();
        bookmarkRepository.findByBookmarkIdAndUserId(deleteBookmark.getBookmarkId(), userId);
        hashService.deleteMappingHashAndBookamrkByBookmarkDeleted(deleteBookmark.getBookmarkId());
        deleteBookmark.setState(0);
        deleteBookmark.setUserId(userId);


        bookmarkRepository.save(deleteBookmark);
        bookmarks.add(deleteBookmark);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }
}
