package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class UserRequestDto {

    @NotBlank
    private String account;
    @NotBlank
    @ToString.Exclude
    private String f_password;
    @NotBlank
    @ToString.Exclude
    private String s_password;
    @NotBlank
    private String name;
    @PositiveOrZero
    private int agree;

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getF_password() {
        return f_password;
    }

    public String getS_password() {
        return s_password;
    }

    public int getAgree() {
        return agree;
    }
}
