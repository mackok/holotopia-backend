package nhl.stenden.exception;

/**
 * This class provides a custom exception for when an incorrect properties are used
 */
public class IncorrectPropertiesException extends RuntimeException {

    public IncorrectPropertiesException(String message){
        super(message);
    }
}
