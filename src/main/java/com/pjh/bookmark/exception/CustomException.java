package com.pjh.bookmark.exception;

import com.pjh.bookmark.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class CustomException extends RuntimeException{
    public CustomException(ErrorCode errorCode) {
        throw new ResponseStatusException(
                errorCode.getHttpStatus(),
                errorCode.getDetail()
        );
    }
}
