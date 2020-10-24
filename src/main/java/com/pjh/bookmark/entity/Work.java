package com.pjh.bookmark.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="work")
public class Work {
    @Id
    @Column(name="work_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workId;

    @Column(name="work_title", nullable = false)
    private String workTitle;

    @Column(name="work_content", length = 1000)
    private String workContent;

    @Column(name="work_progress")
    private int workProgress;

    @Column(name="work_state")
    private int workState;

    @Column(name="work_importance")
    private int workImportance;

    @Column(name="request_ur_id", nullable = false)
    private long requestUserId;

    @Column(name ="worker_ur_id", nullable = false)
    private long workerUserId;

    @Column(name="work_make_date")
    private Date workMakeDate;

    @Column(name = "work_start_date")
    private Date workStartDate;

    @Column(name="work_end_date")
    private Date workEndDate;

    public Work() {
        this.workImportance = 0;
        this.workProgress = 0;
        this.workState = 1;
    }

    public Date getWorkEndDate() {
        return workEndDate;
    }

    public Date getWorkMakeDate() {
        return workMakeDate;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public int getWorkImportance() {
        return workImportance;
    }

    public int getWorkProgress() {
        return workProgress;
    }

    public int getWorkState() {
        return workState;
    }

    public long getRequestUserId() {
        return requestUserId;
    }

    public long getWorkerUserId() {
        return workerUserId;
    }

    public long getWorkId() {
        return workId;
    }

    public String getWorkContent() {
        return workContent;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setRequestUserId(long requestUserId) {
        this.requestUserId = requestUserId;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public void setWorkEndDate(Date workEndDate) {
        this.workEndDate = workEndDate;
    }

    public void setWorkerUserId(long workerUserId) {
        this.workerUserId = workerUserId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }

    public void setWorkImportance(int workImportance) {
        this.workImportance = workImportance;
    }

    public void setWorkMakeDate(Date workMakeDate) {
        this.workMakeDate = workMakeDate;
    }

    public void setWorkProgress(int workProgress) {
        this.workProgress = workProgress;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    public void setWorkState(int workState) {
        this.workState = workState;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }
}

