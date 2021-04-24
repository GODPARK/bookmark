package com.pjh.bookmark.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookmarkException extends RuntimeException {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    public BookmarkException(String message) {
        super(message);
        logger.error(message);
    }
    public BookmarkException() {
        super("Bookmark Exception");
    }
}
