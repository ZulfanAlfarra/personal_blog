package com.personal.web.exceptions;

import com.personal.web.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            Map<String, List<String>> errors
    ) {
        return ResponseEntity.status(status)
                .body(
                        new ErrorResponse(
                                status.value(),
                                errors,
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                Map.of("global", List.of(ex.getMessage()))
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerDuplicateException(DuplicateException ex){
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                Map.of(
                        "global", List.of(ex.getMessage())
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerValidException(MethodArgumentNotValidException ex){
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();

            errors.computeIfAbsent(field, f -> new ArrayList<>()).add(message);
        });

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerConflictException(ConflictException ex){
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                Map.of(
                        "global", List.of(ex.getMessage())
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerBadCredentialException(BadCredentialsException ex){
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                Map.of(
                        "global", List.of("Username or password is incorrect")
                )
        );
    }



}
