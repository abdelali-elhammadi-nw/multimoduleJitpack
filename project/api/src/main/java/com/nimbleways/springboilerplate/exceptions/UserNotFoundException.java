package com.nimbleways.springboilerplate.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    private static final ErrorCode ERROR_CODE = ErrorCode.from("user_not_found");
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final ErrorMessage DEFAULT_MESSAGE = ErrorMessage.from("User not found");

    public UserNotFoundException(final String username) {
        this(DEFAULT_MESSAGE, username);
    }

    public UserNotFoundException(final ErrorMessage message) {
        this(message, null);
    }

    public UserNotFoundException(final ErrorMessage message, final String username){
        super(ERROR_CODE, HTTP_STATUS, message, new UserNotFoundMetadata(username));
    }

    private record UserNotFoundMetadata(String credentiel) {
            public String toString(){return String.format("{Credentiel=%s}", credentiel);}
    }
}
