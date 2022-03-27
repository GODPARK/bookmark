package com.pjh.bookmark.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_ACCOUNT_IS_EMPTY(HttpStatus.BAD_REQUEST, "User Account is empty or null"),
    USER_ACCOUNT_IS_OVERLAP(HttpStatus.BAD_REQUEST, "User Account is already exist"),
    USER_PASSWORD_NOT_MATCH_WITH_SECOND(HttpStatus.BAD_REQUEST, "First Password and Second Password not matched"),
    USER_NAME_IS_EMPTY(HttpStatus.BAD_REQUEST, "User Name is empty");

    private final HttpStatus httpStatus;
    private final String detail;
}
