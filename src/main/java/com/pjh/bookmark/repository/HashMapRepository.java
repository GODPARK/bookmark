package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.HashMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashMapRepository  extends JpaRepository<HashMap, Long> {
}
