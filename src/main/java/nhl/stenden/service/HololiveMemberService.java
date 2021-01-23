package nhl.stenden.service;

import nhl.stenden.dto.HololiveMemberDTO;
import nhl.stenden.dto.VideoDTO;
import nhl.stenden.exception.IncorrectPathVariableException;
import nhl.stenden.handler.SpringExceptionHandler;
import nhl.stenden.model.HololiveMember;
import nhl.stenden.repository.HololiveMemberRepository;
import nhl.stenden.util.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service that handles the business logic of requests related to hololive members.
 */
@Service
public class HololiveMemberService {

    private final HololiveMemberRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public HololiveMemberService(HololiveMemberRepository repository, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    /**
     * Maps a hololive member DTO to a model and adds it to the database.
     * @param memberDTO the DTO of the hololive member that should be added
     */
    public void addMember(HololiveMemberDTO memberDTO){
        HololiveMember member = modelMapper.map(memberDTO, HololiveMember.class);
        SpringExceptionHandler.checkMemberProperties(member);
        repository.addMember(member);
    }

    /**
     * Maps a hololive member DTO to a model and updates it in the database.
     * @param memberId the ID of the hololive member that should be updated
     * @param memberDTO the hololive member, with updated data, that should be updated in the database
     */
    public void updateMember(Long memberId, HololiveMemberDTO memberDTO){
        HololiveMember member = modelMapper.map(memberDTO, HololiveMember.class);
        member.setId(memberId);
        SpringExceptionHandler.checkMemberId(repository, memberId);
        SpringExceptionHandler.checkMemberProperties(member);
        repository.updateMember(member);
    }

    /**
     * Deletes a hololive member from the database.
     * @param memberId the ID of the member that should be deleted
     */
    @Transactional
    public void deleteMember(Long memberId){
        HololiveMember member = repository.getMemberById(memberId);

        if(member == null){
            throw new IncorrectPathVariableException(String.format("there is no member with the ID '%s'", memberId));
        }

        repository.deleteMember(member);
    }

    /**
     * Gets all videos of a hololive member.
     * @param memberId the ID of the hololive member whose videos should be retrieved
     * @return a list containing all videos of the hololive member
     */
    @Transactional
    public List<VideoDTO> getMemberVideos(Long memberId){
        HololiveMember member = repository.getMemberById(memberId);

        if(member == null){
            throw new IncorrectPathVariableException(String.format("there is no member with the ID '%s'", memberId));
        }

        return ModelMapperUtil.mapAll(member.getVideos(), VideoDTO.class, modelMapper);
    }
}
