package com.pjh.bookmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthException extends RuntimeException{
    public UnAuthException(String message) {
        super(message);
    }
}
