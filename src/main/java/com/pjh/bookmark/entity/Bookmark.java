package com.pjh.bookmark.entity;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;


@Entity
@Table(name="bookmark")
public class Bookmark {
    @Id
    @Column(name="bm_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookmarkId;

    @Column(name="bm_name", nullable = false)
    private String bookmarkName;

    @Column(name="bm_url", nullable = false)
    private String url;

    @Column(name="bm_icon")
    private String bookmarkIcon;

    @Column(name="bm_info")
    private String bookmarkInfo;

    @Column(name="ur_id")
    private long userId;

    @Column(name="bm_state")
    private int state;

    @Column(name="bm_main")
    private int isMain;

    @Column(name="bm_freq")
    private long frequency;

    public Bookmark(){
        this.state = 1;
        this.frequency = 0;
        this.isMain = 0;
    }

    public long getUserId() {
        return userId;
    }

    public int getState() {
        return state;
    }

    public long getBookmarkId() {
        return bookmarkId;
    }

    public String getBookmarkIcon() {
        return bookmarkIcon;
    }

    public String getBookmarkInfo() {
        return bookmarkInfo;
    }

    public String getBookmarkName() {
        return bookmarkName;
    }

    public int getIsMain() {
        return isMain;
    }

    public String getUrl() {
        return url;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
}
