package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.HashKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashKeyRepository extends JpaRepository<HashKey, Long> {
    HashKey findByHashNameAndUserIdAndState(String hashName, long userId, int state);
    HashKey findByHashIdAndState(long hashId, int state);
    HashKey findByHashIdAndUserId(long hashId, long userId);
    List<HashKey> findByUserIdAndState(long userId, int state);
    List<HashKey> findByUserIdAndHashMain(long userId, int hashMain);
}
