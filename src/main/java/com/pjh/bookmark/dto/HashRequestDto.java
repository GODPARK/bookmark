package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.HashKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HashRequestDto {
    private List<HashKey> hashKeyList;
}
