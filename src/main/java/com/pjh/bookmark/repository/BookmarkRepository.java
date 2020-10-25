package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserIdAndState(long userId, int state);
    List<Bookmark> findByUserIdAndIsMainAndState(long userId, int isMain, int state);
    Long countByBookmarkIdAndState(long bookmarkId, int state);
    Bookmark findByBookmarkIdAndUserId(long bookmarkId, long userId );
    Bookmark findByBookmarkId(long bookmarkId );

    @Query("SELECT b.bookmarkId FROM Bookmark b where b.userId = :userId and b.state = :state")
    List<Long> findByUserIdAndStateOnlyBookmarkId(@Param("userId") long userId, @Param("state") int state);
}
