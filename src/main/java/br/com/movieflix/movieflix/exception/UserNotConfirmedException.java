package br.com.movieflix.movieflix.exception;

public class UserNotConfirmedException extends RuntimeException{
    public UserNotConfirmedException(String message) {
        super(message);
    }
}
