package com.sweets.leaderboard_compvis.infrastructure.exceptions;

import com.sweets.leaderboard_compvis.infrastructure.models.DTO.ErrorResponse;
import com.sweets.leaderboard_compvis.infrastructure.models.DTO.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(ex.getStatus().value());
        errorResponse.setError(ex.getClass().getSimpleName());
        errorResponse.setTimestamp(Instant.now());

        logger.warn("ERROR: {}", errorResponse, ex);

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleUncaughtException(Exception exception) {
        MessageResponse messageResponse = new MessageResponse("An error has occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
    }
}
