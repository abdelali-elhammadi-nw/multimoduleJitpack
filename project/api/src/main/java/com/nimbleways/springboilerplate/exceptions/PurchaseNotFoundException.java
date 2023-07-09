package com.nimbleways.springboilerplate.exceptions;

import org.springframework.http.HttpStatus;

public class PurchaseNotFoundException extends  BaseException{
    private static final ErrorCode ERROR_CODE = ErrorCode.from("purchase_not_found");
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final ErrorMessage DEFAULT_MESSAGE = ErrorMessage.from("Purchase not found");

    public PurchaseNotFoundException(final String id) {
        this(DEFAULT_MESSAGE, id);
    }

    public PurchaseNotFoundException(final ErrorMessage message) {
        this(message, null);
    }

    public PurchaseNotFoundException(final ErrorMessage message, final String id){
        super(ERROR_CODE, HTTP_STATUS, message, new PurchaseNotFoundMetadata(id));
    }

    private record PurchaseNotFoundMetadata(String id) {
        public String toString(){return String.format("{Id=%s}", id);}
    }
}
