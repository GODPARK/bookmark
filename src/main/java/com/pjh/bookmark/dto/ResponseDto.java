package com.pjh.bookmark.dto;

import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class ResponseDto<E> {
    public boolean code;
    public String message;
    public E data;
}
