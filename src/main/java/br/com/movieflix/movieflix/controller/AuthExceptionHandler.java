package br.com.movieflix.movieflix.controller;

import br.com.movieflix.movieflix.exception.UserNotConfirmedException;
import br.com.movieflix.movieflix.exception.UsernameOrPasswordInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UserNotConfirmedException.class)
    public ResponseEntity<String> handleUserNotConfirmed(UserNotConfirmedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    public ResponseEntity<String> handleInvalidCredentials(UsernameOrPasswordInvalidException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
