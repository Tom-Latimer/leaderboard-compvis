package com.sweets.leaderboard_compvis.infrastructure.exceptions;

import com.sweets.leaderboard_compvis.infrastructure.models.DTO.ErrorResponse;
import com.sweets.leaderboard_compvis.infrastructure.models.DTO.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {

        logger.warn("ERROR: {}", ex, ex);

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(ex.getStatus().value());
        errorResponse.setErrorCode(ex.getErrorCode());
        errorResponse.setTimestamp(Instant.now());

        //logger.warn("ERROR: {}", errorResponse, ex);

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    //handle spring DTO validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        //get validation property errors and add them to map
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorCode("ERR_ARG_INVALID");
        errorResponse.setTimestamp(Instant.now());

        //add validation error map to response
        //to be used by frontend to display errors on forms
        errorResponse.setValidationErrors(errors);

        logger.warn("ERROR: {}", errorResponse, ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setErrorCode("ERR_CRED_INVALID");
        errorResponse.setTimestamp(Instant.now());

        logger.warn("ERROR: {}", errorResponse, ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleUncaughtException(Exception exception) {
        MessageResponse messageResponse = new MessageResponse("An error has occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
    }
}
