package br.com.movieflix.movieflix.exception;

public class EmailAlreadyExist extends RuntimeException{
    public EmailAlreadyExist(String message) {
        super(message);
    }
}
