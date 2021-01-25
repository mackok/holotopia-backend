package nhl.stenden.handler;

import nhl.stenden.exception.IncorrectPathVariableException;
import nhl.stenden.exception.IncorrectPropertiesException;
import nhl.stenden.model.HololiveMember;
import nhl.stenden.repository.HololiveMemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class handles the custom spring exception
 */
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

    /**
     * Throws an exception when a hololive member object doesn't contain all necessary properties.
     * @param member the hololive member that should be checked
     */
    public static void checkMemberProperties(HololiveMember member){
        if(member.getName() == null || member.getChannel() == null || member.getUploads() == null){
            throw new IncorrectPropertiesException("'name', 'channel' and 'uploads' are necessary properties and can not be null");
        }
    }

    /**
     * Throws an exception when there is no hololive member in the database with the given ID.
     * @param repository the repository that should be used to check the ID
     * @param memberId the ID of the hololive member
     */
    public static void checkMemberId(HololiveMemberRepository repository, Long memberId){
        if(repository.getMemberById(memberId) == null){
            throw new IncorrectPathVariableException(String.format("there is no member with the ID '%s'", memberId));
        }
    }

    /**
     * Creates and returns the error message.
     * @param error the error that has occured
     * @return a String containing the error message
     */
    private String createErrorMessage(String error){
        return String.format("An error with the message: \"%s\" has returned.", error);
    }
}
