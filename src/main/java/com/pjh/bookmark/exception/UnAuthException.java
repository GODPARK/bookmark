package com.pjh.bookmark.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthException extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    public UnAuthException(String message) {
        super(message);
        logger.error(message);
    }

    public UnAuthException(String message, String path, String remoteUser) {
        super(message);
        logger.error("path: " + path + ", remote user : " + remoteUser + " msg: " + message);
    }

    public UnAuthException(){
        super("Auth Fail");
        logger.error("Auth fail");
    }
}
