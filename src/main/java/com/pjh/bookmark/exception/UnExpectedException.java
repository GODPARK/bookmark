package com.pjh.bookmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnExpectedException extends RuntimeException {

    public UnExpectedException(String msg){
        super(msg);
    }
}
