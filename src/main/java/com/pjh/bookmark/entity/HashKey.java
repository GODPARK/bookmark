package com.pjh.bookmark.entity;

import javax.persistence.*;

@Entity
@Table(name="hashkey")
public class HashKey {
    @Id
    @Column(name="hash_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hashId;

    @Column(name ="hash_name", nullable = false)
    private String hashName;

    @Column(name="hash_state", nullable = false)
    private int state;

    @Column(name="user_id", nullable = false)
    private long userId;

    public HashKey(){
        this.state = 1;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setHashId(long hashId) {
        this.hashId = hashId;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public long getHashId() {
        return hashId;
    }

    public long getUserId() {
        return userId;
    }

    public String getHashName() {
        return hashName;
    }
}