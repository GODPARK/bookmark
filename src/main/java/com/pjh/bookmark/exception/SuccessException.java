package com.pjh.bookmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED)
public class SuccessException extends RuntimeException {
    public SuccessException(String message) {
        super(message);
    }
}
