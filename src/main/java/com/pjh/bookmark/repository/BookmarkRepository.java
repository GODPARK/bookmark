package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserIdAndState(long userId, int state);
    Long countByBookmarkIdAndState(long bookmarkId, int state);
    Bookmark findByBookmarkId(long bookmarkId );
}
