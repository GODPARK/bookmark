package com.pjh.bookmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HashException extends RuntimeException{
    public HashException(String message) {
        super(message);
    }

    public HashException(){
        super("Hash Exception Accure");
    }
}
