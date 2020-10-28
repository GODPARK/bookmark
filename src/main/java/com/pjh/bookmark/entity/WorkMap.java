package com.pjh.bookmark.entity;


import javax.persistence.*;

@Entity
@Table(name="workmap")
public class WorkMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hash_seq", nullable = false)
    private long seq;

    @Column(name = "hash_id", nullable = false)
    private long hashId;

    @Column(name="work_id", nullable = false)
    private long workId;

    public long getHashId() {
        return hashId;
    }

    public long getSeq() {
        return seq;
    }

    public long getWorkId() {
        return workId;
    }

    public void setHashId(long hashId) {
        this.hashId = hashId;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }
}
