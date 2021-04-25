package com.pjh.bookmark.service;

import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.exception.BookmarkException;
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

@Service
public class BookmarkService {

    private static final int LIVE_BOOKMARK_STATE = 1;
    private static final int DEAD_BOOKMARK_STATE = 0;
    private static final int MAIN_BOOKMARK_NUM = 1;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private HashMapRepository hashMapRepository;

    @Autowired
    private HashService hashService;

    public List<Bookmark> totalBookmarkListFunc(long userId) {
        return bookmarkRepository.findByUserIdAndState(userId, LIVE_BOOKMARK_STATE);
    }

    public List<Bookmark> mainBookmarkListFunc(long userId) {
        return bookmarkRepository.findByUserIdAndIsMainAndState(userId, MAIN_BOOKMARK_NUM, LIVE_BOOKMARK_STATE);
    }

    public void addBookmarkFrequencyFunc(long bookmarkId, long userId) {
        Bookmark bookmark = bookmarkRepository.findByBookmarkIdAndUserIdAndState(bookmarkId, userId, LIVE_BOOKMARK_STATE);
        if (bookmark != null) {
            bookmark.setFrequency(bookmark.getFrequency() + 1);
            bookmarkRepository.save(bookmark);
            throw new SuccessException("freq add success");
        } else {
            throw new BookmarkException("freq add fail");
        }
    }

    public Bookmark createBookmarkFunc(Bookmark bookmark, long userId) {
        Bookmark saveBookmark = bookmark;
        saveBookmark.setUserId(userId);
        saveBookmark.setFrequency(0);
        return bookmarkRepository.save(saveBookmark);
    }

    public Bookmark updateBookmarkFunc(Bookmark bookmark, long userId) {
        Bookmark updateBookmark = bookmarkRepository.findByBookmarkIdAndUserIdAndState(bookmark.getBookmarkId(), userId, LIVE_BOOKMARK_STATE);
        if (updateBookmark == null) throw new BookmarkException("update bookmark not found");
        if (bookmark.getBookmarkIcon() != null && !bookmark.getBookmarkIcon().equals(""))
            updateBookmark.setBookmarkIcon(bookmark.getBookmarkIcon());
        if (bookmark.getBookmarkInfo() != null && !bookmark.getBookmarkInfo().equals(""))
            updateBookmark.setBookmarkInfo(bookmark.getBookmarkInfo());
        if (bookmark.getBookmarkName() != null && !bookmark.getBookmarkName().equals(""))
            updateBookmark.setBookmarkName(bookmark.getBookmarkName());
        if (bookmark.getUrl() != null && !bookmark.getUrl().equals("")) updateBookmark.setUrl(bookmark.getUrl());
        return bookmarkRepository.save(updateBookmark);
    }

    public Bookmark deleteBookmarkFunc(long bookmarkId, long userId) {
        Bookmark deleteBookmark = bookmarkRepository.findByBookmarkIdAndUserIdAndState(bookmarkId, userId, LIVE_BOOKMARK_STATE);
        if (deleteBookmark == null) throw new BookmarkException("delete bookmark not found");
        hashService.deleteHashMapByBookmarkFunc(deleteBookmark.getBookmarkId());
        deleteBookmark.setState(DEAD_BOOKMARK_STATE);
        return bookmarkRepository.save(deleteBookmark);
    }

    public List<Bookmark> bookmarkListNotHashMapFunc(long userId) {
        List<Long> bookmarkIdListInBookmark = bookmarkRepository.findByUserIdAndStateOnlyBookmarkId(userId, LIVE_BOOKMARK_STATE);
        List<Long> bookmarkIdListInHashMap = hashMapRepository.findALLOnlyBookmarkId();
        bookmarkIdListInBookmark.removeAll(bookmarkIdListInHashMap);

        List<Bookmark> bookmarkList = new ArrayList<>();
        for (Long bookmarkId : bookmarkIdListInBookmark) {
            Bookmark bookmark = bookmarkRepository.findByBookmarkIdAndUserIdAndState(bookmarkId, userId, LIVE_BOOKMARK_STATE);
            if (bookmark != null) {
                bookmarkList.add(bookmark);
            }
        }
        return bookmarkList;
    }

    public List<Bookmark> searchBookmarkListByName(String bookmarkName, long userId) {
        if (bookmarkName != null && !bookmarkName.equals("")) {
            return bookmarkRepository.findByBookmarkNameContainsAndUserIdAndState(bookmarkName, userId, LIVE_BOOKMARK_STATE);
        } else {
            return bookmarkRepository.findByUserIdAndState(userId, LIVE_BOOKMARK_STATE);
        }
    }
}
