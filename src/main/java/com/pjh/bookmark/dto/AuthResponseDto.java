package com.pjh.bookmark.dto;

import lombok.*;

@ToString @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String account;
}
