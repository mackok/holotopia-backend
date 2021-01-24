package nhl.stenden.exception;

/**
 * This class provides a custom exception for when an incorrect pathvariable is used
 */
public class IncorrectPathVariableException extends RuntimeException {

    public IncorrectPathVariableException(String message){
        super(message);
    }
}
