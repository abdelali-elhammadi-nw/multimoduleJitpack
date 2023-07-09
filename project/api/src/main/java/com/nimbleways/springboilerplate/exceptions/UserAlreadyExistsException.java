package com.nimbleways.springboilerplate.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
    private static final ErrorCode ERROR_CODE = ErrorCode.from("user_already_exists");
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final ErrorMessage DEFAULT_MESSAGE = ErrorMessage.from("User already exits");

    public UserAlreadyExistsException(final String username) {
        this(DEFAULT_MESSAGE, username);
    }

    public UserAlreadyExistsException(final ErrorMessage message) {
        this(message, null);
    }

    public UserAlreadyExistsException(final ErrorMessage message, final String username){
        super(ERROR_CODE, HTTP_STATUS, message, new UserAlreadyExitsMetadata(username));
    }

    private record UserAlreadyExitsMetadata(String email) {
        public String toString(){return String.format("{Email=%s}", email);}
    }
}
