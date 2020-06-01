package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserIdAndState(long userId, int state);
    List<Bookmark> findByUserIdAndIsMainAndState(long userId, int isMain, int state);
    Long countByBookmarkIdAndState(long bookmarkId, int state);
    Bookmark findByBookmarkIdAndUserId(long bookmarkId, long userId );
    Bookmark findByBookmarkId(long bookmarkId );
}
