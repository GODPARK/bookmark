package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.BookmarkRequestDto;
import com.pjh.bookmark.dto.BookmarkResponseDto;
import com.pjh.bookmark.dto.HashRequestDto;
import com.pjh.bookmark.dto.HashResponseDto;
import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.entity.HashMap;
import com.pjh.bookmark.exception.HashException;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.HashKeyRepository;
import com.pjh.bookmark.repository.HashMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HashService {

    @Autowired
    private HashKeyRepository hashKeyRepository;

    @Autowired
    private HashMapRepository hashMapRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public HashResponseDto selectByUser(long userId){
        HashResponseDto hashResponseDto = new HashResponseDto();
        List<HashKey> hashKeyList = hashKeyRepository.findByUserIdAndState(userId,1);
        hashResponseDto.setHashKeyList(hashKeyList);
        return hashResponseDto;
    }

    public HashResponseDto selectByBookmark(long bookmarkId){
        HashResponseDto hashResponseDto = new HashResponseDto();
        List<HashMap> hashMapList = hashMapRepository.findByBookmarkId(bookmarkId);
        List<HashKey> hashKeyList = new ArrayList<>();
        for( HashMap hashMap : hashMapList ) {
            HashKey hashKey = hashKeyRepository.findByHashIdAndState(hashMap.getHashId(),1);
            hashKeyList.add(hashKey);
        }
        hashResponseDto.setHashKeyList(hashKeyList);
        return hashResponseDto;
    }

    public BookmarkResponseDto selectByHashId(long hashId ){
        BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto();

        List<HashMap> hashMapList = hashMapRepository.findByHashId(hashId);
        List<Bookmark> bookmarks = new ArrayList<>();
        for ( HashMap hashMap : hashMapList) {
            bookmarks.add(bookmarkRepository.findByBookmarkId(hashMap.getBookmarkId()));
        }
        bookmarkResponseDto.setBookmarkList(bookmarks);
        return bookmarkResponseDto;
    }

    public HashResponseDto saveMappingHashAndBookmark(HashRequestDto hashRequestDto, long userId){

        //Bookmark ID 존대 여부 확인
        if(bookmarkRepository.countByBookmarkIdAndState(hashRequestDto.getBookmarkId(),1) ==0 ){
            throw new HashException("Not Found By BookmarkId");
        }


        for( HashKey hashKey : hashRequestDto.getHashKeyList()){
            // 이미 존재하는 hash key 인지 확인
            hashKey.setHashName(hashKey.getHashName().replace(" ","").toLowerCase());
            HashKey checkHash = hashKeyRepository.findByHashNameAndUserIdAndState(hashKey.getHashName(), userId,1);

            if(checkHash == null){
                hashKey.setState(1);
                hashKey.setUserId(userId);
                checkHash = hashKeyRepository.save(hashKey);
            }
            else {
                if(hashKey.getHashId() != checkHash.getHashId()) {
                    throw new HashException("wrong hash infomation");
                }
            }

            HashMap hashMap = new HashMap();
            hashMap.setBookmarkId(hashRequestDto.getBookmarkId());
            hashMap.setHashId(checkHash.getHashId());

            // 이미 매핑 이력 있는지 조사
            if(hashMapRepository.countByHashIdAndBookmarkId(checkHash.getHashId(),hashRequestDto.getBookmarkId()) == 0){
                hashMapRepository.save(hashMap);
            }
        }
        return this.selectByBookmark(hashRequestDto.getBookmarkId());
    }

    public HashResponseDto saveHashKey(HashRequestDto hashRequestDto, long userId) {
        List<HashKey> hashKeyList = new ArrayList<>();
        HashResponseDto hashResponseDto = new HashResponseDto();
        for ( HashKey hashKey : hashRequestDto.getHashKeyList()) {
            if( hashKey.getHashName().equals("")) {
                throw new HashException("hash key name is empty");
            }

            // 이미 존재하는 hash key 인지 확인
            hashKey.setHashName(hashKey.getHashName().replace(" ","").toLowerCase());
            HashKey checkHash = hashKeyRepository.findByHashNameAndUserIdAndState(hashKey.getHashName(), userId,1);

            if(checkHash == null){
                hashKey.setState(1);
                hashKey.setUserId(userId);
                checkHash = hashKeyRepository.save(hashKey);
                hashKeyList.add(checkHash);
                hashResponseDto.setHashKeyList(hashKeyList);
            }
        }
        return hashResponseDto;
    }

    public HashResponseDto updateHashKey(HashRequestDto hashRequestDto, long userId) {
        List<HashKey> hashKeyList = new ArrayList<>();
        for ( HashKey hashKey : hashRequestDto.getHashKeyList()) {
            HashKey targetHash  = hashKeyRepository.findByHashIdAndUserId(hashKey.getHashId(), userId);
            if(targetHash != null) {
                targetHash.setHashName(hashKey.getHashName());
                targetHash.setHashMain(hashKey.getHashMain());
                HashKey resultHash = hashKeyRepository.save(targetHash);
                hashKeyList.add(resultHash);
            }
        }
        HashResponseDto hashResponseDto = new HashResponseDto();
        hashResponseDto.setHashKeyList(hashKeyList);
        return hashResponseDto;
    }

    public HashResponseDto deleteMappingHash(HashRequestDto hashRequestDto) {
        List<HashMap> hashMapList = new ArrayList<>();
        for ( HashKey hashKey: hashRequestDto.getHashKeyList()){
            HashMap hashMap = hashMapRepository.findByHashIdAndBookmarkId(hashKey.getHashId(),hashRequestDto.getBookmarkId());
            if(hashMap != null){
                hashMapList.add(hashMap);
            }
            else {
                throw new HashException("Not Found Delete target");
            }
        }
        hashMapRepository.deleteAll(hashMapList);
        return this.selectByBookmark(hashRequestDto.getBookmarkId());
    }

    public void deleteMappingHashAndBookamrkByBookmarkDeleted(long bookmarkId){
        List<HashMap> hashMapList = hashMapRepository.findByBookmarkId(bookmarkId);
        if(hashMapList.isEmpty()){
            throw new HashException("Not Found By BookmarkId");
        }
        else {
            hashMapRepository.deleteAll(hashMapList);
        }
    }

    public ResponseEntity deleteMappingHashAndBookmarkByHashKeyDeleted(HashRequestDto hashRequestDto) {
        for( HashKey hashKey : hashRequestDto.getHashKeyList()) {
            List<HashMap> hashMapList = hashMapRepository.findByHashId(hashKey.getHashId());
            if(!hashMapList.isEmpty()){
                hashMapRepository.deleteAll(hashMapList);
            }

            HashKey deleteHashKey = hashKeyRepository.findByHashIdAndState(hashKey.getHashId(), 1);
            if(deleteHashKey != null) {
                deleteHashKey.setState(0);
                hashKeyRepository.save(deleteHashKey);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public HashResponseDto mainHashListByUserId(long userId) {
        HashResponseDto hashResponseDto = new HashResponseDto();
        hashResponseDto.setHashKeyList(hashKeyRepository.findByUserIdAndHashMain(userId,1));
        return hashResponseDto;
    }
}
