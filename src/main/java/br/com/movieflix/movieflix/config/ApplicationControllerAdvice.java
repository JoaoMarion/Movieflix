package br.com.movieflix.movieflix.config;

import br.com.movieflix.movieflix.exception.EmailAlreadyExist;
import br.com.movieflix.movieflix.exception.UserNotFound;
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

    @ExceptionHandler({EmailAlreadyExist.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handEmailAlreadyExist(EmailAlreadyExist ex){
        return ex.getMessage();
    }

    @ExceptionHandler({UserNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handuserNotFound(UserNotFound ex){
        return ex.getMessage();
    }

}
