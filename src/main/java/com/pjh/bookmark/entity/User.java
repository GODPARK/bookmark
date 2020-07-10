package com.pjh.bookmark.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name="user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name="user_account", nullable = false, unique = true, length = 30)
    private String userAccount;

    @Column(name="user_password", nullable = false)
    private String userPassword;

    @Column(name="user_name", nullable = false, length = 30)
    private String userName;

    @Column(name="user_state", nullable = false)
    private int state;

    @Column(name="user_role", nullable = false)
    private long userRole;

    @Column(name="user_create", nullable = false)
    private Date userCreate;

    @Column(name="user_agree", nullable = false)
    private int userAgree;

    @OneToOne(mappedBy = "user")
    private Token token;

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public long getUserRole() {
        return userRole;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUserCreate(Date userCreate) {
        this.userCreate = userCreate;
    }

    public void setUserRole(long userRole) {
        this.userRole = userRole;
    }

    public int getUserAgree() {
        return userAgree;
    }

    public void setUserAgree(int userAgree) {
        this.userAgree = userAgree;
    }

    public Date getUserCreate() {
        return userCreate;
    }
}
