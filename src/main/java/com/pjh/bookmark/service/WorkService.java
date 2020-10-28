package com.pjh.bookmark.service;

import com.pjh.bookmark.entity.Work;
import com.pjh.bookmark.exception.WorkException;
import com.pjh.bookmark.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;

    private static final int LIVE_WORK_STATE = 1;

    public List<Work> totalWorkListFunc() {
        return workRepository.findByWorkState(LIVE_WORK_STATE);
    }

    public Work workByWorkIdFunc(long workId, long userId) {
        Work work = workRepository.findByWorkIdAndWorkState(workId, LIVE_WORK_STATE);
        if(work == null) throw new WorkException("work is not found");
        return work;
    }
}
