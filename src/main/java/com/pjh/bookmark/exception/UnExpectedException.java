package com.pjh.bookmark.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnExpectedException extends RuntimeException {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    public UnExpectedException(String message){
        super(message);
        logger.error(message);
    }
}
