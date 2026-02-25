package com.comeon.group.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameNotExistException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(GameNotExistException ex) {
    	ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                "Game does not exist. Please enter the correct game id .",
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(PlayerNotExistException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(PlayerNotExistException ex) {
    	ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                "Player does not exist. Please enter the correct Player id to process the request.",
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyLoveException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(AlreadyLoveException ex) {
    	ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                "Player already loved this game. Please choose another player Id or game Id to process the request.",
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                LocalDateTime.now()
                
                // TODO: conflict or not acceptable or forbidden ?
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                "Server Error. Please try again",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
