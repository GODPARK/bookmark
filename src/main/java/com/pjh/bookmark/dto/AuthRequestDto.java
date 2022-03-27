package com.pjh.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class AuthRequestDto {
    @NotBlank
    private String account;
    @NotBlank
    @JsonIgnore
    private String password;
}
