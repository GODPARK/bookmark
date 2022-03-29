package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.HashKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface HashKeyRepository extends JpaRepository<HashKey, Long> {
    Optional<HashKey> findByHashNameAndUserIdAndState(String hashName, long userId, int state);
    Optional<HashKey> findByHashIdAndUserIdAndState(long hashId, long userId, int state);
    List<HashKey> findByUserIdAndState(long userId, int state);
    List<HashKey> findByUserIdAndHashMainAndState(long userId, int hashMain, int state);
}
