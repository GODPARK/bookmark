package com.pjh.bookmark.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HashException extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    public HashException(String message) {
        super(message);
        logger.error(message);
    }

    public HashException(){
        super("Hash Exception Accure");
    }
}
