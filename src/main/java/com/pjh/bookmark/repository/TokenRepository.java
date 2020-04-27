package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findByUserId(long userId);
}
