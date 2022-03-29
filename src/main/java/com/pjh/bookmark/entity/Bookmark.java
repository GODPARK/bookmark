package com.pjh.bookmark.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;


@Entity
@Table(name="bookmark")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class Bookmark {
    @Id
    @Column(name="bm_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookmarkId;

    @NotBlank
    @Column(name="bm_name", nullable = false)
    private String bookmarkName;

    @NotBlank
    @Column(name="bm_url", nullable = false)
    private String url;

    @Column(name="bm_icon")
    private String bookmarkIcon;

    @Column(name="bm_info")
    private String bookmarkInfo;

    @Positive
    @Column(name="ur_id")
    private long userId;

    @Column(name="bm_state")
    private int state;

    @Column(name="bm_main")
    private int isMain;

    @Column(name="bm_freq")
    private long frequency;

    @Column(name="bm_create_date")
    private Date createDate;

    @Column(name="bm_update_date")
    private Date updateDate;

    @Column(name="bm_delete_date")
    private Date deleteDate;
}
