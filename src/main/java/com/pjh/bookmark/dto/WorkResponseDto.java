package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Work;

import java.util.ArrayList;
import java.util.List;

public class WorkResponseDto {
    private List<Work> workList;

    public List<Work> getWorkList() {
        return workList;
    }

    public WorkResponseDto() {}
    public WorkResponseDto(Work work) {
        if(work != null) {
            this.workList = new ArrayList<>();
            this.workList.add(work);
        }
    }
    public void setWorkList(List<Work> workList) {
        this.workList = workList;
    }
}
