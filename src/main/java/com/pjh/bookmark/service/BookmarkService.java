package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.exception.SuccessException;
import com.pjh.bookmark.exception.UnExpectedException;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.HashMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Bookmark> totalBookmarkListFunc(long userId){
        return bookmarkRepository.findByUserIdAndState(userId, LIVE_BOOKMARK_STATE);
    }

    public List<Bookmark> mainBookmarkListFunc(long userId){
        return bookmarkRepository.findByUserIdAndIsMainAndState(userId,MAIN_BOOKMARK_NUM, LIVE_BOOKMARK_STATE);
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

    public Bookmark createBookmarkFunc(BookmarkRequestDto bookmarkRequestDto, long userId){
        Bookmark saveBookmark = bookmarkRequestDto.getBookmark();
        saveBookmark.setUserId(userId);
        return bookmarkRepository.save(saveBookmark);
    }

    public Bookmark updateBookmarkFunc(BookmarkRequestDto bookmarkRequestDto, long userId){
        //TODO: 하나만 업데이트 할 수 있도록 수정 필요
        Bookmark updateBookmark = bookmarkRequestDto.getBookmark();
        updateBookmark.setUserId(userId);
        updateBookmark.setState(LIVE_BOOKMARK_STATE);
        return bookmarkRepository.save(updateBookmark);
    }

    public Bookmark deleteBookmarkFunc(long bookmarkId, long userId){
        Bookmark deleteBookmark = bookmarkRepository.findByBookmarkIdAndUserId(bookmarkId, userId);
        hashService.deleteHashMapByBookmarkFunc(deleteBookmark.getBookmarkId());
        deleteBookmark.setState(DEAD_BOOKMARK_STATE);
        deleteBookmark.setUserId(userId);
        return bookmarkRepository.save(deleteBookmark);
    }

    public List<Bookmark> bookmarkListNotHashMapFunc(long userId) {
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
        return bookmarkList;
    }
}
