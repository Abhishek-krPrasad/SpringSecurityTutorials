package com.avi.UsingSpringSecurityDefault.advices;

import com.avi.UsingSpringSecurityDefault.exceptions.ResourceNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(resourceNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(error,error.getStatus());
    }



    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException authenticationException){
        ApiError error = ApiError.builder()
                .message(authenticationException.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .build();

        return new ResponseEntity<>(error,error.getStatus());
    }




}
