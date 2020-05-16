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
}
