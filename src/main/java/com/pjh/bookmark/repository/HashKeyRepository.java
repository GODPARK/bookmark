package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.HashKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashKeyRepository extends JpaRepository<HashKey, Long> {
    HashKey findByHashNameAndUserIdAndState(String hashName, long userId, int state);
    HashKey findByHashIdAndState(long hashId, int state);
}