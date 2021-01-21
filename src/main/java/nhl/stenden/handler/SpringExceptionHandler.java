package nhl.stenden.handler;

import nhl.stenden.exception.IncorrectPathVariableException;
import nhl.stenden.exception.IncorrectPropertiesException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SpringExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectPropertiesException.class)
    public String handleIncorrectPropertiesException(IncorrectPropertiesException e){
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectPathVariableException.class)
    public String handleIncorrectPathVariableException(IncorrectPathVariableException e){
        return createErrorMessage(e.getMessage());
    }

    private String createErrorMessage(String error){
        return String.format("An error with the message: \"%s\" has returned.", error);
    }
}
