package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.Bookmark;
import com.pjh.bookmark.entity.HashKey;
import lombok.*;

import java.util.List;

@Getter @Setter @Builder @ToString @NoArgsConstructor @AllArgsConstructor
public class CombinationResponseDto {
    private List<Bookmark> bookmarkList;
    private List<HashKey> hashKeyList;
}
