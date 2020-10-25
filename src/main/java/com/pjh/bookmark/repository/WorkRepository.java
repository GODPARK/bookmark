package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByWorkState(int state);
    Work findByWorkIdAndWorkState(long workId, int state);
}
