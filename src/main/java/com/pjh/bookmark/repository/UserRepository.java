package com.pjh.bookmark.repository;

import com.pjh.bookmark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserIdAndState(long userId, long state );
    User findByUserAccountAndState(String userAccount, long state);
}
