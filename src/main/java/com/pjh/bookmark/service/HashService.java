package com.pjh.bookmark.service;

import com.pjh.bookmark.common.ErrorCode;
import com.pjh.bookmark.common.StatusCode;
import com.pjh.bookmark.dto.HashRequestDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.entity.HashMap;
import com.pjh.bookmark.exception.CustomException;
import com.pjh.bookmark.exception.HashException;
import com.pjh.bookmark.exception.SuccessException;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.HashKeyRepository;
import com.pjh.bookmark.repository.HashMapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HashService {

    private static final int LIVE_HASHKEY_STATE = 1;
    private static final int DEAD_HASHKEY_STATE = 0;
    private static final int LIVE_BOOKMARK_STATE = 1;
    private static final int MAIN_HASHKEY_NUM = 1;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private HashKeyRepository hashKeyRepository;

    @Autowired
    private HashMapRepository hashMapRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkService bookmarkService;

    public HashKey searchHashKeyById(long hashKeyId, long userId) {
        Optional<HashKey> hashKey = this.hashKeyRepository.findByHashIdAndUserIdAndState(
                hashKeyId, userId, StatusCode.ACTIVE_HASH_STATE.getState()
        );
        if (hashKey.isPresent()) return hashKey.get();
        else throw new CustomException(ErrorCode.HASHKEY_IS_EMPTY);
    }

    public HashKey searchHashKeyByHashName(String hashKeyName, long userId) {
        Optional<HashKey> hashKey = this.hashKeyRepository.findByHashNameAndUserIdAndState(
                hashKeyName, userId, StatusCode.ACTIVE_HASH_STATE.getState()
        );
        if (hashKey.isPresent()) return hashKey.get();
        else throw new CustomException(ErrorCode.HASHKEY_IS_EMPTY);
    }

    public List<HashKey> hashKeyListByUserFunc(long userId) {
        return hashKeyRepository.findByUserIdAndState(userId, StatusCode.ACTIVE_HASH_STATE.getState());
    }

    public List<HashKey> hashKeyListByBookmarkFunc(long bookmarkId, long userId) {
        List<HashMap> hashMapList = hashMapRepository.findByBookmarkId(bookmarkId);
        List<HashKey> hashKeyList = new ArrayList<>();
        for (HashMap hashMap : hashMapList) {
            HashKey hashKey = this.searchHashKeyById(hashMap.getHashId(), userId);
            hashKeyList.add(hashKey);
        }
        return hashKeyList;
    }

    public List<Bookmark> bookmarkListByHashKeyFunc(long hashId) {
        List<HashMap> hashMapList = hashMapRepository.findByHashId(hashId);
        List<Bookmark> bookmarkList = new ArrayList<>();
        for (HashMap hashMap : hashMapList) {
            Optional<Bookmark> bookmark = bookmarkRepository.findByBookmarkId(hashMap.getBookmarkId());
            if (bookmark.isPresent()) bookmarkList.add(bookmark.get());
        }
        bookmarkList.sort(new Comparator<Bookmark>() {
            @Override
            public int compare(Bookmark o1, Bookmark o2) {
                if (o1.getFrequency() == o2.getFrequency()) return 0;
                else if (o1.getFrequency() < o2.getFrequency()) return 1;
                else return -1;
            }
        });
        return bookmarkList;
    }

    public List<HashKey> createHashMapAndBookmarkFunc(List<HashKey> hashKeyList, long userId, long bookmarkId) {
        //Bookmark ID 존대 여부 확인
        Bookmark bookmark = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, userId);

        for (HashKey hashKey : hashKeyList) {
            // 이미 존재하는 hash key 인지 확인
            hashKey.setHashName(hashKey.getHashName().replace(" ", "").toLowerCase());
            HashKey checkHash = this.searchHashKeyById(hashKey.getHashId(), userId);
            hashKey.setState(LIVE_HASHKEY_STATE);
            hashKey.setUserId(userId);
            checkHash = hashKeyRepository.save(hashKey);

            HashMap hashMap = new HashMap();
            hashMap.setBookmarkId(bookmark.getBookmarkId());
            hashMap.setHashId(checkHash.getHashId());

            // 이미 매핑 이력 있는지 조사
            if (hashMapRepository.countByHashIdAndBookmarkId(checkHash.getHashId(), bookmark.getBookmarkId()) == 0) {
                hashMapRepository.save(hashMap);
            }
        }
        return this.hashKeyListByBookmarkFunc(bookmark.getBookmarkId() , userId);
    }

    public List<HashKey> editMappingHashAndBookmark(List<HashKey> hashKeyList, long bookmarkId, long userId) {
        logger.info("edit mapping hash and bookmark");
        Bookmark bookmark = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, userId);
        List<HashMap> currentHashMapList = hashMapRepository.findByBookmarkId(bookmark.getBookmarkId());
        List<Long> dbHashIdList = new ArrayList<>();
        List<Long> requestHashIdList = new ArrayList<>();
        for (HashMap hashMap : currentHashMapList) {
            dbHashIdList.add(hashMap.getHashId());
        }
        for (HashKey hashKey : hashKeyList) {
            requestHashIdList.add(hashKey.getHashId());
        }
        if (requestHashIdList.containsAll(dbHashIdList) && dbHashIdList.containsAll(requestHashIdList)) {
            return this.hashKeyListByBookmarkFunc(bookmark.getBookmarkId(), userId);
        } else {
            List<Long> deleteTargetList = new ArrayList<>(dbHashIdList);
            List<Long> createTargetList = new ArrayList<>(requestHashIdList);
            deleteTargetList.removeAll(requestHashIdList);
            createTargetList.removeAll(dbHashIdList);
            for (Long deleteId : deleteTargetList) {
                hashMapRepository.deleteByHashIdAndBookmarkId(deleteId, bookmark.getBookmarkId());
            }
            for (Long createId : createTargetList) {
                HashMap createHashMap = new HashMap();
                createHashMap.setHashId(createId);
                createHashMap.setBookmarkId(bookmark.getBookmarkId());
                if (hashMapRepository.countByHashIdAndBookmarkId(createHashMap.getHashId(), bookmark.getBookmarkId()) == 0) {
                    hashMapRepository.save(createHashMap);
                }
            }
        }
        return this.hashKeyListByBookmarkFunc(bookmark.getBookmarkId(), userId);
    }

    @CacheEvict(value = "main_hash", allEntries = true)
    public HashKey createHashKeyFunc(HashKey hashKey, long userId) {
        // 이미 존재하는 hash key 인지 확인
        hashKey.setHashName(hashKey.getHashName().replace(" ", "").toLowerCase());
        Optional<HashKey> checkHash = this.hashKeyRepository.findByHashNameAndUserIdAndState(hashKey.getHashName(), userId, StatusCode.ACTIVE_HASH_STATE.getState());
        if (!checkHash.isPresent()) {
            hashKey.setState(StatusCode.ACTIVE_HASH_STATE.getState());
            hashKey.setUserId(userId);
            hashKey.setCreateDate(new Date());
            return hashKeyRepository.save(hashKey);
        } else {
            throw new CustomException(ErrorCode.HASHKEY_IS_ALREADY_EXSIST);
        }
    }

    @CacheEvict(value = "main_hash", allEntries = true)
    public HashKey updateHashKeyFunc(HashKey hashKey, long userId) {
        HashKey targetHash = this.searchHashKeyById(hashKey.getHashId(), userId);
        targetHash.setHashName(hashKey.getHashName());
        targetHash.setHashMain(hashKey.getHashMain());
        targetHash.setUpdateDate(new Date());
        return hashKeyRepository.save(targetHash);
    }

    public List<HashKey> deleteHashMapFunc(List<HashKey> hashKeyList, long bookmarkId, long userId) {
        List<HashMap> hashMapList = new ArrayList<>();
        Bookmark bookmark = this.bookmarkService.searchBookmarkByIdAndUserId(bookmarkId, userId);
        for (HashKey hashKey : hashKeyList) {
            HashMap hashMap = hashMapRepository.findByHashIdAndBookmarkId(hashKey.getHashId(), bookmark.getBookmarkId());
            if (hashMap != null) {
                hashMapList.add(hashMap);
            } else {
                throw new HashException("Not Found Delete target");
            }
        }
        hashMapRepository.deleteAll(hashMapList);
        return this.hashKeyListByBookmarkFunc(bookmark.getBookmarkId(), userId);
    }

    public void deleteHashMapByBookmarkFunc(long bookmarkId) {
        List<HashMap> hashMapList = hashMapRepository.findByBookmarkId(bookmarkId);
        if (!hashMapList.isEmpty()) {
            hashMapRepository.deleteAll(hashMapList);
        }
    }

    @CacheEvict(value = "main_hash", allEntries = true)
    public HashKey deleteHashMapAndHashKeyFunc(long hashId, long userId) {
        HashKey deleteHashKey = this.searchHashKeyById(hashId, userId);
        if (deleteHashKey == null) throw new HashException("hash key is not found");
        List<HashMap> hashMapList = hashMapRepository.findByHashId(deleteHashKey.getHashId());
        if (!hashMapList.isEmpty()) {
            hashMapRepository.deleteAll(hashMapList);
        }
        deleteHashKey.setState(DEAD_HASHKEY_STATE);
        return hashKeyRepository.save(deleteHashKey);
    }

    @Cacheable(value = "main_hash")
    public List<HashKey> mainHashKeyListFunc(long userId) {
        logger.info("refresh main hash");
        List<HashKey> hashKeyList = hashKeyRepository.findByUserIdAndHashMainAndState(userId, MAIN_HASHKEY_NUM, LIVE_HASHKEY_STATE);
        hashKeyList.sort(new Comparator<HashKey>() {
            @Override
            public int compare(HashKey o1, HashKey o2) {
                return o1.getHashName().compareTo(o2.getHashName());
            }
        });
        return hashKeyList;
    }
}
