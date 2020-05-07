package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.exception.NotHaveTagException;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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

        List<Bookmark> bookmarks = new ArrayList<>();
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();
        for (Bookmark indiBookmark : bookmarkRequestDto.getBookmarkList()){
            if(tagRepository.findByTagId(indiBookmark.getTagId()) != null) {
                bookmarks.add(indiBookmark);
            }
            else {
                throw new NotHaveTagException(String.valueOf(indiBookmark.getTagId()));
            }
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
            if(tagRepository.findByTagId(indiBookmark.getTagId()) != null){
                indiBookmark.setState(1);
                bookmarks.add(indiBookmark);
            }
            else{
                throw new NotHaveTagException(String.valueOf(indiBookmark.getTagId()));
            }
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
            bookmark.setState(0);
            bookmark.setTagId(-1);
            bookmarks.add(bookmark);
        }
        bookmarkRepository.saveAll(bookmarks);
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }
}
