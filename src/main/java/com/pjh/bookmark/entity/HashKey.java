package com.pjh.bookmark.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name="hashkey")
@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class HashKey {
    @Id
    @Column(name="hash_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hashId;

    @NotBlank
    @Column(name ="hash_name", nullable = false)
    private String hashName;

    @Column(name="hash_state", nullable = false)
    private int state;

    @Column(name="hash_main", nullable = false)
    private int hashMain;

    @Column(name="user_id", nullable = false)
    private long userId;

    @Column(name="create_date", nullable = true)
    private Date createDate;

    @Column(name="update_date", nullable = true)
    private Date updateDate;

    @Column(name="delete_date", nullable = true)
    private Date deleteDate;
}