package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.exception.SuccessException;
import com.pjh.bookmark.exception.UnExpectedException;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.HashKeyRepository;
import com.pjh.bookmark.repository.HashMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookmarkService {

    private static final int LIVE_BOOKMARK_STATE = 1;
    private static final int DEAD_BOOKMARK_STATE = 0;
    private static final int MAIN_BOOKMARK_NUM = 1;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private HashMapRepository hashMapRepository;

    @Autowired
    private HashService hashService;

    public BookmarkResponseDto totalBookmarkListFunc(long userId){
        return new BookmarkResponseDto(bookmarkRepository.findByUserIdAndState(userId, LIVE_BOOKMARK_STATE));
    }

    public BookmarkResponseDto mainBookmarkListFunc(long userId){
        return new BookmarkResponseDto(bookmarkRepository.findByUserIdAndIsMainAndState(userId,MAIN_BOOKMARK_NUM, LIVE_BOOKMARK_STATE));
    }

    public void addBookmarkFrequencyFunc(long bookmarkId, long userId){
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

    public BookmarkResponseDto createBookmarkFunc(BookmarkRequestDto bookmarkRequestDto, long userId){
        Bookmark saveBookmark = bookmarkRequestDto.getBookmark();
        saveBookmark.setUserId(userId);
        return new BookmarkResponseDto(bookmarkRepository.save(saveBookmark));
    }

    public BookmarkResponseDto updateBookmarkFunc(BookmarkRequestDto bookmarkRequestDto, long userId){
        //TODO: 하나만 업데이트 할 수 있도록 수정 필요
        Bookmark updateBookmark = bookmarkRequestDto.getBookmark();
        updateBookmark.setUserId(userId);
        updateBookmark.setState(LIVE_BOOKMARK_STATE);
        return new BookmarkResponseDto(bookmarkRepository.save(updateBookmark));
    }

    public BookmarkResponseDto deleteBookmarkFunc(BookmarkRequestDto bookmarkRequestDto, long userId){
        Bookmark deleteBookmark = bookmarkRequestDto.getBookmark();
        bookmarkRepository.findByBookmarkIdAndUserId(deleteBookmark.getBookmarkId(), userId);
        hashService.deleteHashMapByBookmarkFunc(deleteBookmark.getBookmarkId());
        deleteBookmark.setState(DEAD_BOOKMARK_STATE);
        deleteBookmark.setUserId(userId);
        return new BookmarkResponseDto(bookmarkRepository.save(deleteBookmark));
    }

    public BookmarkResponseDto bookmarkListNotHashMapFunc(long userId) {
        List<Long> bookmarkIdListInBookmark = bookmarkRepository.findByUserIdAndStateOnlyBookmarkId(userId, LIVE_BOOKMARK_STATE);
        List<Long> bookmarkIdListInHashMap = hashMapRepository.findALLOnlyBookmarkId();
        bookmarkIdListInBookmark.removeAll(bookmarkIdListInHashMap);

        List<Bookmark> bookmarkList = new ArrayList<>();
        for (Long bookmarkId : bookmarkIdListInBookmark ) {
            Bookmark bookmark = bookmarkRepository.findByBookmarkIdAndUserId(bookmarkId, userId);
            if ( bookmark != null ) {
                bookmarkList.add(bookmark);
            }
        }
        return new BookmarkResponseDto(bookmarkList);
    }
}
