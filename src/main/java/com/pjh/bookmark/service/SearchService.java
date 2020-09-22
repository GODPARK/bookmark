package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.SearchRequestDto;
import com.pjh.bookmark.entity.Search;
import com.pjh.bookmark.exception.SuccessException;
import com.pjh.bookmark.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    public void saveSearchRecord(SearchRequestDto searchRequestDto, long userId) {
        Search search = searchRequestDto.getSearch();
        search.setSearchDate(new Date());
        search.setUserId(userId);

        searchRepository.save(search);

        throw new SuccessException("Search Record is Success");
    }
}
