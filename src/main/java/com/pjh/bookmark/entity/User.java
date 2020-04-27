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

    @Column(name="user_password", nullable = false, length = 40)
    private String userPassword;

    @Column(name="user_name", nullable = false, length = 30)
    private String userName;

    @Column(name="user_state", nullable = false)
    private int state;

    @Column(name="user_role", nullable = false)
    private long userRole;

    @Column(name="user_create", nullable = false)
    private Date userCreate;

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
}
