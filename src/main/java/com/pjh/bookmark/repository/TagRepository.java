package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    List<Tag> findByUserIdAndState(long userId, int state);
    Tag findByTagId(long tagId);
}
