package com.tony.user_service.config;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tony.user_service.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(RuntimeException ex) {
        
        if (ex.getMessage().equals("El correo ya está registrado")) {
            ErrorResponse response = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); 
        }
        
        HttpStatus status = HttpStatus.NOT_FOUND; 
        
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        
        ErrorResponse response = new ErrorResponse("Error de validación: " + errores);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}