package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.HashRequestDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.entity.HashMap;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    @Cacheable(value = "hash")
    public List<HashKey> hashKeyListByUserFunc(long userId) {
        return hashKeyRepository.findByUserIdAndState(userId, LIVE_HASHKEY_STATE);
    }

    public List<HashKey> hashKeyListByBookmarkFunc(long bookmarkId, long userId) {
        List<HashMap> hashMapList = hashMapRepository.findByBookmarkId(bookmarkId);
        List<HashKey> hashKeyList = new ArrayList<>();
        for (HashMap hashMap : hashMapList) {
            HashKey hashKey = hashKeyRepository.findByHashIdAndUserIdAndState(hashMap.getHashId(), userId, LIVE_HASHKEY_STATE);
            hashKeyList.add(hashKey);
        }
        return hashKeyList;
    }

    public List<Bookmark> bookmarkListByHashKeyFunc(long hashId) {
        List<HashMap> hashMapList = hashMapRepository.findByHashId(hashId);
        List<Bookmark> bookmarkList = new ArrayList<>();
        for (HashMap hashMap : hashMapList) {
            bookmarkList.add(bookmarkRepository.findByBookmarkId(hashMap.getBookmarkId()));
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

    public List<HashKey> createHashMapAndBookmarkFunc(HashRequestDto hashRequestDto, long userId) {
        //Bookmark ID 존대 여부 확인
        if (bookmarkRepository.countByBookmarkIdAndUserIdAndState(hashRequestDto.getBookmarkId(), userId, LIVE_BOOKMARK_STATE) == 0) {
            throw new HashException("Not Found By BookmarkId");
        }
        for (HashKey hashKey : hashRequestDto.getHashKeyList()) {
            // 이미 존재하는 hash key 인지 확인
            hashKey.setHashName(hashKey.getHashName().replace(" ", "").toLowerCase());
            HashKey checkHash = hashKeyRepository.findByHashNameAndUserIdAndState(hashKey.getHashName(), userId, LIVE_HASHKEY_STATE);

            if (checkHash == null) {
                hashKey.setState(LIVE_HASHKEY_STATE);
                hashKey.setUserId(userId);
                checkHash = hashKeyRepository.save(hashKey);
            } else {
                if (hashKey.getHashId() != checkHash.getHashId()) {
                    throw new HashException("wrong hash infomation");
                }
            }

            HashMap hashMap = new HashMap();
            hashMap.setBookmarkId(hashRequestDto.getBookmarkId());
            hashMap.setHashId(checkHash.getHashId());

            // 이미 매핑 이력 있는지 조사
            if (hashMapRepository.countByHashIdAndBookmarkId(checkHash.getHashId(), hashRequestDto.getBookmarkId()) == 0) {
                hashMapRepository.save(hashMap);
            }
        }
        return this.hashKeyListByBookmarkFunc(hashRequestDto.getBookmarkId(), userId);
    }

    public List<HashKey> editMappingHashAndBookmark(HashRequestDto hashRequestDto, long userId) {
        logger.info("edit mapping hash and bookmark");
        if (bookmarkRepository.countByBookmarkIdAndUserIdAndState(hashRequestDto.getBookmarkId(), userId, LIVE_BOOKMARK_STATE) == 0) {
            throw new HashException("Not Found By BookmarkId");
        }
        List<HashMap> currentHashMapList = hashMapRepository.findByBookmarkId(hashRequestDto.getBookmarkId());
        List<Long> dbHashIdList = new ArrayList<>();
        List<Long> requestHashIdList = new ArrayList<>();
        for (HashMap hashMap : currentHashMapList) {
            dbHashIdList.add(hashMap.getHashId());
        }
        for (HashKey hashKey : hashRequestDto.getHashKeyList()) {
            requestHashIdList.add(hashKey.getHashId());
        }
        if (requestHashIdList.containsAll(dbHashIdList) && dbHashIdList.containsAll(requestHashIdList)) {
            return this.hashKeyListByBookmarkFunc(hashRequestDto.getBookmarkId(), userId);
        } else {
            List<Long> deleteTargetList = new ArrayList<>(dbHashIdList);
            List<Long> createTargetList = new ArrayList<>(requestHashIdList);
            deleteTargetList.removeAll(requestHashIdList);
            createTargetList.removeAll(dbHashIdList);
            for (Long deleteId : deleteTargetList) {
                hashMapRepository.deleteByHashIdAndBookmarkId(deleteId, hashRequestDto.getBookmarkId());
            }
            for (Long createId : createTargetList) {
                HashMap createHashMap = new HashMap();
                createHashMap.setHashId(createId);
                createHashMap.setBookmarkId(hashRequestDto.getBookmarkId());
                if (hashMapRepository.countByHashIdAndBookmarkId(createHashMap.getHashId(), hashRequestDto.getBookmarkId()) == 0) {
                    hashMapRepository.save(createHashMap);
                }
            }
        }
        return this.hashKeyListByBookmarkFunc(hashRequestDto.getBookmarkId(), userId);
    }

    @CacheEvict(value = "hash", allEntries = true)
    public HashKey createHashKeyFunc(HashKey hashKey, long userId) {
        if (hashKey.getHashName().equals("")) {
            throw new HashException("hash key name is empty");
        }
        // 이미 존재하는 hash key 인지 확인
        hashKey.setHashName(hashKey.getHashName().replace(" ", "").toLowerCase());
        HashKey checkHash = hashKeyRepository.findByHashNameAndUserIdAndState(hashKey.getHashName(), userId, LIVE_HASHKEY_STATE);
        if (checkHash == null) {
            hashKey.setState(LIVE_HASHKEY_STATE);
            hashKey.setUserId(userId);
            return hashKeyRepository.save(hashKey);
        } else {
            throw new HashException("hash name already exist");
        }
    }

    @CacheEvict(value = "hash", allEntries = true)
    public HashKey updateHashKeyFunc(HashKey hashKey, long userId) {
        HashKey targetHash = hashKeyRepository.findByHashIdAndUserIdAndState(hashKey.getHashId(), userId, LIVE_HASHKEY_STATE);
        if (targetHash == null) throw new HashException("hash is not found");
        targetHash.setHashName(hashKey.getHashName());
        targetHash.setHashMain(hashKey.getHashMain());
        return hashKeyRepository.save(targetHash);
    }

    public List<HashKey> deleteHashMapFunc(HashRequestDto hashRequestDto, long userId) {
        List<HashMap> hashMapList = new ArrayList<>();
        for (HashKey hashKey : hashRequestDto.getHashKeyList()) {
            HashMap hashMap = hashMapRepository.findByHashIdAndBookmarkId(hashKey.getHashId(), hashRequestDto.getBookmarkId());
            if (hashMap != null) {
                hashMapList.add(hashMap);
            } else {
                throw new HashException("Not Found Delete target");
            }
        }
        hashMapRepository.deleteAll(hashMapList);
        return this.hashKeyListByBookmarkFunc(hashRequestDto.getBookmarkId(), userId);
    }

    public void deleteHashMapByBookmarkFunc(long bookmarkId) {
        List<HashMap> hashMapList = hashMapRepository.findByBookmarkId(bookmarkId);
        if (!hashMapList.isEmpty()) {
            hashMapRepository.deleteAll(hashMapList);
        }
    }

    @CacheEvict(value = "hash", allEntries = true)
    public HashKey deleteHashMapAndHashKeyFunc(long hashId, long userId) {
        HashKey deleteHashKey = hashKeyRepository.findByHashIdAndUserIdAndState(hashId, userId, LIVE_HASHKEY_STATE);
        if (deleteHashKey == null) throw new HashException("hash key is not found");
        List<HashMap> hashMapList = hashMapRepository.findByHashId(deleteHashKey.getHashId());
        if (!hashMapList.isEmpty()) {
            hashMapRepository.deleteAll(hashMapList);
        }
        deleteHashKey.setState(DEAD_HASHKEY_STATE);
        return hashKeyRepository.save(deleteHashKey);
    }

    @Cacheable(value = "hash")
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
