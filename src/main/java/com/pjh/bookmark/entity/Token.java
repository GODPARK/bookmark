package com.pjh.bookmark.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="token")
public class Token {
    @Id
    @Column(name="user_id", nullable = false)
    private long userId;

    @Column(name="token", nullable = false)
    private String token;

    @Column(name="token_expire", nullable = false)
    private long tokenExpire;

    @Column(name="token_timestamp", nullable = false)
    private Date tokenTimestamp;

    public String getToken() {
        return token;
    }

    public long getUserId() {
        return userId;
    }

    public long getTokenExpire() {
        return tokenExpire;
    }

    public Date getTokenTimestamp() {
        return tokenTimestamp;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenExpire(long tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTokenTimestamp(Date tokenTimestamp) {
        this.tokenTimestamp = tokenTimestamp;
    }
}
