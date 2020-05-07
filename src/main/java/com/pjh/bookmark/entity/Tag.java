package com.pjh.bookmark.entity;

import javax.persistence.*;

@Entity
@Table(name="tag")
public class Tag {
    @Id
    @Column(name="tag_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Column(name="tag_name", nullable = false)
    private String tagName;

    @Column(name="tag_prior")
    private int tagPriority;

    @Column(name="tag_state")
    private int state;

    @Column(name="ur_id")
    private long userId;

    public int getState() {
        return state;
    }

    public long getTagId() {
        return tagId;
    }

    public long getUserId() {
        return userId;
    }

    public int getTagPriority() {
        return tagPriority;
    }

    public String getTagName() {
        return tagName;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
