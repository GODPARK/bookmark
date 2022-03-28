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
    USER_NAME_IS_EMPTY(HttpStatus.BAD_REQUEST, "User Name is empty"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User is not found"),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Token is not found"),
    ACCOUNT_OR_PASSWORD_IS_INCORRECT(HttpStatus.UNAUTHORIZED, "account or password is incorrect"),
    EMPTY_AUTH_TOKEN_IN_HEADER(HttpStatus.BAD_REQUEST, "auth token is empty"),
    BOOKMARK_FREQ_FAIL(HttpStatus.NOT_MODIFIED, "bookmark freq add fail"),
    BOOKMARK_IS_EMPTY(HttpStatus.NOT_FOUND, "bookmark is not found")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
