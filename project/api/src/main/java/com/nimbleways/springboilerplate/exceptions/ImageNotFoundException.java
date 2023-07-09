package com.nimbleways.springboilerplate.exceptions;

import org.springframework.http.HttpStatus;

public class ImageNotFoundException extends BaseException{


        private static final ErrorCode ERROR_CODE = ErrorCode.from("image_not_found");
        private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
        private static final ErrorMessage DEFAULT_MESSAGE = ErrorMessage.from("Image not found");

        public ImageNotFoundException(){
                super(ERROR_CODE, HTTP_STATUS, DEFAULT_MESSAGE);
        }

}
