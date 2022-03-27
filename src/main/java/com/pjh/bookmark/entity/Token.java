package com.pjh.bookmark.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="token")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
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

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
