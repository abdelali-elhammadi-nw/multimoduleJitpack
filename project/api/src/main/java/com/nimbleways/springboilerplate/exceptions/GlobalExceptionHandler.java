package com.nimbleways.springboilerplate.exceptions;

import com.nimbleways.springboilerplate.dto.exception.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Map;
import java.util.stream.Collectors;

import static com.nimbleways.springboilerplate.utils.ExceptionLoggingHelper.log;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link BaseException} exceptions and returns a JSON formatted response.
     *
     * @param ex      : the ex
     * @param request : the request on which the ex occurred
     * @return a JSON formatted response containing the ex details and additional fields
     */
    @ExceptionHandler({BaseException.class})
    public ResponseEntity<Object> handleBaseException(final BaseException ex, final ServletWebRequest request) {
        log(ex, request);
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode(ex.getErrorCode().value())
                .httpStatusCode(ex.getHttpStatus().value())
                .errorMetadata(ex.getErrorMetadata())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponseDto);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException ex,
                                                                       final ServletWebRequest request) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode("max_upload_size_exceeded")
                .message("the max upload size has been exceeded")
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDto);
    }

    @ExceptionHandler({InvalidImageException.class})
    public ResponseEntity<Object> handleInvalidImageException(final InvalidImageException ex,
                                                                       final ServletWebRequest request) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode("invalid_image_type")
                .message("invalid image type")
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex, final ServletWebRequest request) {
        log(ex, request);
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode("access_denied")
                .httpStatusCode(HttpStatus.FORBIDDEN.value())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorResponseDto handleValidationExceptions(final MethodArgumentNotValidException ex, final ServletWebRequest request) {
        log(ex, request);

        final Map<String, String> invalidFields = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(error -> ((FieldError) error).getField(),
                        this::getDefaultMessage));

        return ErrorResponseDto.builder()
                .errorCode("bad_request")
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .errorMetadata(invalidFields)
                .message("invalid fields")
                .build();
    }


    private String getDefaultMessage(final ObjectError error) {
        return error.getDefaultMessage() == null ? "" :  error.getDefaultMessage();
    }

    /**
     * Handles uncaught {@link Exception} exceptions and returns a JSON formatted response.
     *
     * @param ex      : the ex
     * @param request : the request on which the ex occurred
     * @return a JSON formatted response containing the ex details
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUncaughtException(final Exception ex, final ServletWebRequest request) {
        log(ex, request);
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode("uncaught_exception")
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }
}
