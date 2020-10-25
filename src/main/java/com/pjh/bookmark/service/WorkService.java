package com.pjh.bookmark.service;

import com.pjh.bookmark.dto.WorkResponseDto;
import com.pjh.bookmark.entity.Work;
import com.pjh.bookmark.exception.WorkException;
import com.pjh.bookmark.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;

    private static final int LIVE_WORK_STATE = 1;

    public WorkResponseDto totalWorkListFunc() {
        List<Work> workList = workRepository.findByWorkState(LIVE_WORK_STATE);
        WorkResponseDto workResponseDto = new WorkResponseDto();
        workResponseDto.setWorkList(workList);
        return workResponseDto;
    }

    public WorkResponseDto workByWorkIdFunc(long workId, long userId) {
        Work work = workRepository.findByWorkIdAndWorkState(workId, LIVE_WORK_STATE);
        if(work == null) throw new WorkException("work is not found");
        return new WorkResponseDto(work);
    }
}
