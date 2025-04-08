package br.com.movieflix.movieflix.config;

import br.com.movieflix.movieflix.exception.UsernameOrPasswordInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler({UsernameOrPasswordInvalidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNotFoundExceptions(UsernameOrPasswordInvalidException ex){
        return ex.getMessage();
    }

}
