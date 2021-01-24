package unit.handler;

import nhl.stenden.exception.IncorrectPathVariableException;
import nhl.stenden.exception.IncorrectPropertiesException;
import nhl.stenden.handler.SpringExceptionHandler;
import nhl.stenden.model.HololiveMember;
import nhl.stenden.repository.HololiveMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class SpringExceptionHandlerTest {

    private HololiveMember member;

    @BeforeEach
    void init(){
        member = new HololiveMember();
    }

    /**
     * Tests whether no exception is thrown when the given hololive member has a name, channel ID and uploads ID.
     */
    @Test
    void checkMemberPropertiesValidTest(){
        member.setName("Test Member");
        member.setChannel("Test Channel ID");
        member.setUploads("Test Uploads ID");
        Assertions.assertDoesNotThrow(() -> SpringExceptionHandler.checkMemberProperties(member));
    }

    /**
     * Tests whether an IncorrectPropertiesException is thrown when the given hololive member has no name.
     */
    @Test
    void checkMemberPropertiesNoNameTest(){
        member.setChannel("Test Channel ID");
        member.setUploads("Test Uploads ID");
        Assertions.assertThrows(IncorrectPropertiesException.class, () -> SpringExceptionHandler.checkMemberProperties(member));
    }

    /**
     * Tests whether an IncorrectPropertiesException is thrown when the given hololive member has no channel ID.
     */
    @Test
    void checkMemberPropertiesNoChannelTest(){
        member.setName("Test Member");
        member.setUploads("Test Uploads ID");
        Assertions.assertThrows(IncorrectPropertiesException.class, () -> SpringExceptionHandler.checkMemberProperties(member));
    }

    /**
     * Tests whether an IncorrectPropertiesException is thrown when the given hololive member has no uploads ID.
     */
    @Test
    void checkMemberPropertiesNoUploadsTest(){
        member.setName("Test Member");
        member.setChannel("Test Channel ID");
        Assertions.assertThrows(IncorrectPropertiesException.class, () -> SpringExceptionHandler.checkMemberProperties(member));
    }

    /**
     * Tests whether no exception is thrown when a valid member ID is given.
     */
    @Test
    void checkMemberIdValidTest(){
        Long memberId = 1L;
        HololiveMemberRepository mockedRepository = mock(HololiveMemberRepository.class);
        when(mockedRepository.getMemberById(memberId)).thenReturn(new HololiveMember());
        Assertions.assertDoesNotThrow(() -> SpringExceptionHandler.checkMemberId(mockedRepository, memberId));
    }

    /**
     * Tests whether an IncorrectPathVariableException is thrown when an invalid member ID is given.
     */
    @Test
    void checkMemberIdInvalidMemberId(){
        Long memberId = 1L;
        HololiveMemberRepository mockedRepository = mock(HololiveMemberRepository.class);
        when(mockedRepository.getMemberById(memberId)).thenReturn(null);
        Assertions.assertThrows(IncorrectPathVariableException.class, () -> SpringExceptionHandler.checkMemberId(mockedRepository, memberId));
    }
}
