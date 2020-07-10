package com.pjh.bookmark.dto;

import com.pjh.bookmark.entity.User;

public class UserRequestDto {

    private String account;
    private String f_password;
    private String s_password;
    private String name;
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
