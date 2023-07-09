package com.nimbleways.springboilerplate.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidImageException extends BaseException{

    private static final ErrorCode ERROR_CODE = ErrorCode.from("invalid_image");
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final ErrorMessage DEFAULT_MESSAGE = ErrorMessage.from("Invalid image");

    public InvalidImageException(){
        super(ERROR_CODE, HTTP_STATUS, DEFAULT_MESSAGE);
    }

}
