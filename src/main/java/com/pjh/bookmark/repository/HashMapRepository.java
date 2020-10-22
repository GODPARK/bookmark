package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.HashMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HashMapRepository  extends JpaRepository<HashMap, Long> {
    Long countByHashIdAndBookmarkId(long hashId, long bookmarkId);
    @Transactional
    void deleteByHashIdAndBookmarkId(long hashId, long bookmarkId);
    HashMap findByHashIdAndBookmarkId(long hashId, long bookmarkId);
    List<HashMap> findByBookmarkId(long bookmarkId);
    List<HashMap> findByHashId(long hashId);
}
