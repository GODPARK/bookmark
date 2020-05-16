package com.pjh.bookmark.entity;

import javax.persistence.*;

@Entity
@Table(name="hashmap")
public class HashMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hash_seq", nullable = false)
    private long seq;

    @Column(name="hash_id", nullable = false)
    private long hashId;

    @Column(name="bm_id", nullable = false)
    private long bookmarkId;
}
