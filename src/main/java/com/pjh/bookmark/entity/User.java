package com.pjh.bookmark.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString(exclude = "userPassword")
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

    @Column(name="user_update", nullable = true)
    private Date userUpdate;

    @Column(name="user_delete", nullable = true)
    private Date userDelete;

    @Column(name="user_agree", nullable = false)
    private int userAgree;

    @OneToOne(mappedBy = "user")
    private Token token;
}
