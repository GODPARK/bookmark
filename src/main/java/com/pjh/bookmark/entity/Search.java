package com.pjh.bookmark.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="search")
public class Search {
    @Id
    @Column(name="sh_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long searchId;

    @Column(name="sh_word", nullable = false)
    private String searchWord;

    @Column(name="sh_platform", nullable = false)
    private String searchPlatform;

    @Column(name="sh_date")
    private Date searchDate;

    @Column(name="ur_id")
    private long userId;

    public Search() {
        this.searchDate = new Date();
    }

    public long getUserId() {
        return userId;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public long getSearchId() {
        return searchId;
    }

    public String getSearchPlatform() {
        return searchPlatform;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public void setSearchId(long searchId) {
        this.searchId = searchId;
    }

    public void setSearchPlatform(String searchPlatform) {
        this.searchPlatform = searchPlatform;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
