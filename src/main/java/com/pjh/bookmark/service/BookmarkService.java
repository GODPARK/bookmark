package com.pjh.bookmark.service;

import com.pjh.bookmark.common.ErrorCode;
import com.pjh.bookmark.common.StatusCode;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.exception.BookmarkException;
import com.pjh.bookmark.exception.CustomException;
import com.pjh.bookmark.exception.SuccessException;
import com.pjh.bookmark.exception.UnExpectedException;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.HashMapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private HashMapRepository hashMapRepository;

    @Autowired
    private HashService hashService;

    public Bookmark searchBookmarkByIdAndUserId(long bookmarkId, long userId) {
        Optional<Bookmark> bookmark = this.bookmarkRepository.findByBookmarkIdAndUserIdAndState(
                bookmarkId,
                userId,
                StatusCode.ACTIVE_BOOKMARK_STATE.getState()
        );
        if (bookmark.isPresent()) {
            return bookmark.get();
        } else {
            throw new CustomException(ErrorCode.BOOKMARK_IS_EMPTY);
        }
    }

    public List<Bookmark> totalBookmarkListFunc(long userId) {
        return bookmarkRepository.findByUserIdAndState(
                userId,
                StatusCode.ACTIVE_BOOKMARK_STATE.getState()
        );
    }

    public List<Bookmark> mainBookmarkListFunc(long userId) {
        return bookmarkRepository.findByUserIdAndIsMainAndState(
                userId,
                StatusCode.DEFAULT_BOOKMARK_NUM.getState(),
                StatusCode.ACTIVE_BOOKMARK_STATE.getState()
        );
    }

    public String addBookmarkFrequencyFunc(long bookmarkId, long userId) {
        Bookmark bookmark = this.searchBookmarkByIdAndUserId(bookmarkId, userId);
        bookmark.setFrequency(bookmark.getFrequency() + 1);
        bookmarkRepository.save(bookmark);
        return "OK";
    }

    public Bookmark createBookmarkFunc(Bookmark bookmark, long userId) {
        Bookmark saveBookmark = bookmark;
        saveBookmark.setUserId(userId);
        saveBookmark.setFrequency(0);
        return bookmarkRepository.save(saveBookmark);
    }

    public Bookmark updateBookmarkFunc(Bookmark bookmark, long userId) {
        Bookmark updateBookmark = this.searchBookmarkByIdAndUserId(bookmark.getBookmarkId(), userId);
        if (bookmark.getBookmarkIcon() != null && !bookmark.getBookmarkIcon().equals(""))
            updateBookmark.setBookmarkIcon(bookmark.getBookmarkIcon());
        if (bookmark.getBookmarkInfo() != null && !bookmark.getBookmarkInfo().equals(""))
            updateBookmark.setBookmarkInfo(bookmark.getBookmarkInfo());
        if (bookmark.getUrl() != null && !bookmark.getUrl().equals("")) updateBookmark.setUrl(bookmark.getUrl());
        return bookmarkRepository.save(updateBookmark);
    }

    public Bookmark deleteBookmarkFunc(long bookmarkId, long userId) {
        Bookmark deleteBookmark = this.searchBookmarkByIdAndUserId(bookmarkId, userId);
        hashService.deleteHashMapByBookmarkFunc(deleteBookmark.getBookmarkId());
        deleteBookmark.setState(StatusCode.DEACTIVE_BOOKMARK_STATE.getState());
        return bookmarkRepository.save(deleteBookmark);
    }

    public List<Bookmark> bookmarkListNotHashMapFunc(long userId) {
        List<Long> bookmarkIdListInBookmark = bookmarkRepository.findByUserIdAndStateOnlyBookmarkId(userId, StatusCode.ACTIVE_BOOKMARK_STATE.getState());
        List<Long> bookmarkIdListInHashMap = hashMapRepository.findALLOnlyBookmarkId();
        bookmarkIdListInBookmark.removeAll(bookmarkIdListInHashMap);

        List<Bookmark> bookmarkList = new ArrayList<>();
        for (Long bookmarkId : bookmarkIdListInBookmark) {
            Optional<Bookmark> bookmark = bookmarkRepository.findByBookmarkIdAndUserIdAndState(bookmarkId, userId, StatusCode.ACTIVE_BOOKMARK_STATE.getState());
            if (bookmark.isPresent()) {
                bookmarkList.add(bookmark.get());
            }
        }
        return bookmarkList;
    }

    public List<Bookmark> searchBookmarkListByName(String bookmarkName, long userId) {
        if (bookmarkName != null && !bookmarkName.equals("")) {
            return bookmarkRepository.findByBookmarkNameContainsAndUserIdAndState(bookmarkName, userId, StatusCode.ACTIVE_BOOKMARK_STATE.getState());
        } else {
            return bookmarkRepository.findByUserIdAndState(userId, StatusCode.ACTIVE_BOOKMARK_STATE.getState());
        }
    }
}
