package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.exception.NotHaveTagException;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookmarkService {

    static final int NORMAL_STATIC = 1;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private TagRepository tagRepository;

    public BookmarkResponseDto selectAll(long userId){
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        bookmarkResponseDto.setBookmarkList(bookmarkRepository.findByUserIdAndState(userId, NORMAL_STATIC));
        return bookmarkResponseDto;
    }

    public BookmarkResponseDto insertNew(BookmarkRequestDto bookmarkRequestDto){

        if(tagRepository.findByTagId(bookmarkRequestDto.bookmark.getTagId()) != null){
            BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
            List<Bookmark> bookmarks = new ArrayList<>();
            bookmarks.add(bookmarkRepository.save(bookmarkRequestDto.bookmark));
            bookmarkResponseDto.setBookmarkList(bookmarks);
            return bookmarkResponseDto;
        }
        else {
            throw new NotHaveTagException(String.valueOf(bookmarkRequestDto.bookmark.getTagId()));
        }

    }

    public BookmarkResponseDto update(BookmarkRequestDto bookmarkRequestDto){
        if(tagRepository.findByTagId(bookmarkRequestDto.bookmark.getTagId()) != null){
            BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
            List<Bookmark> bookmarks = new ArrayList<>();
            bookmarkRequestDto.bookmark.setState(1);
            bookmarks.add(bookmarkRepository.save(bookmarkRequestDto.bookmark));
            bookmarkResponseDto.setBookmarkList(bookmarks);
            return bookmarkResponseDto;
        }
        else{
            throw new NotHaveTagException(String.valueOf(bookmarkRequestDto.bookmark.getTagId()));
        }
    }

    public BookmarkResponseDto delete(BookmarkRequestDto bookmarkRequestDto){
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        List<Bookmark> bookmarks = new ArrayList<>();
        Bookmark bookmark = bookmarkRepository.findByBookmarkId(bookmarkRequestDto.getBookmark().getBookmarkId());
        bookmark.setState(0);
        bookmark.setTagId(-1);
        bookmarks.add(bookmarkRepository.save(bookmark));
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }
}
