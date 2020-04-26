package com.pjh.bookmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class NotHaveTagException extends RuntimeException{

    public NotHaveTagException(String message){
        super("No Tag Id : " + message);
    }
}
