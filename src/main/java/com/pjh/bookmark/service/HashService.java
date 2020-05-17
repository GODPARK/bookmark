package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.HashRequestDto;
import com.pjh.bookmark.dto.HashResponseDto;
import com.pjh.bookmark.entity.HashKey;
import com.pjh.bookmark.entity.HashMap;
import com.pjh.bookmark.exception.HashException;
import com.pjh.bookmark.repository.BookmarkRepository;
import com.pjh.bookmark.repository.HashKeyRepository;
import com.pjh.bookmark.repository.HashMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public HashResponseDto selectAll(long bookmarkId){
        HashResponseDto hashResponseDto = new HashResponseDto();
        List<HashMap> hashMapList = hashMapRepository.findByBookmarkId(bookmarkId);

        for( HashMap hashMap : hashMapList ) {
            HashKey hashKey = hashKeyRepository.findByHashIdAndState(hashMap.getHashId(),1);
            hashResponseDto.addHashKey(hashKey);
        }
        return hashResponseDto;
    }

    public HashResponseDto saveMappingHashAndBookmark(HashRequestDto hashRequestDto){

        //Bookmark ID 존대 여부 확인
        if(bookmarkRepository.countByBookmarkIdAndState(hashRequestDto.getBookmarkId(),1) ==0 ){
            throw new HashException("Not Found By BookmarkId");
        }

        for( HashKey hashKey : hashRequestDto.getHashKeyList()){
            // 이미 존재하는 hash key 인지 확인
            HashKey checkHash = hashKeyRepository.findByHashNameAndUserIdAndState(hashKey.getHashName(),hashKey.getUserId(),hashKey.getState());
            if(checkHash == null){
                hashKey.setState(1);
                checkHash = hashKeyRepository.save(hashKey);
            }

            HashMap hashMap = new HashMap();
            hashMap.setBookmarkId(hashRequestDto.getBookmarkId());
            hashMap.setHashId(checkHash.getHashId());

            // 이미 매핑 이력 있는지 조사
            if(hashMapRepository.countByHashIdAndBookmarkId(checkHash.getHashId(),hashRequestDto.getBookmarkId()) == 0){
                hashMapRepository.save(hashMap);
            }
        }
        return this.selectAll(hashRequestDto.getBookmarkId());
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
        return this.selectAll(hashRequestDto.getBookmarkId());
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

    public void deleteMappingHashAndBookmarkByHashKeyDeleted(long hashId) {
        List<HashMap> hashMapList = hashMapRepository.findByHashId(hashId);
        if(hashMapList.isEmpty()){
            throw new HashException("Not Found By HashId");
        }
        else {
            hashMapRepository.deleteAll(hashMapList);
            HashKey hashKey = hashKeyRepository.findByHashIdAndState(hashId,1);
            hashKey.setState(0);
            hashKeyRepository.save(hashKey);
        }
    }
}