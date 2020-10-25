package com.pjh.bookmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WorkException extends RuntimeException {
    public WorkException(String message) { super(message); }
    public WorkException() { super("Work Exception Accure"); }
}
