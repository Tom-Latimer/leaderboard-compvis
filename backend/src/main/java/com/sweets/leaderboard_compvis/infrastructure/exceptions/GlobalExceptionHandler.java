package com.sweets.leaderboard_compvis.infrastructure.exceptions;

import com.sweets.leaderboard_compvis.infrastructure.models.DTO.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleUncaughtException(Exception exception) {
        MessageResponse messageResponse = new MessageResponse("An error has occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
    }
}
