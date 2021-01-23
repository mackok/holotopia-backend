package nhl.stenden.service;

import nhl.stenden.dto.HololiveMemberDTO;
import nhl.stenden.dto.VideoDTO;
import nhl.stenden.model.HololiveMember;
import nhl.stenden.repository.HololiveMemberRepository;
import nhl.stenden.util.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
     * @param hololiveMember the DTO of the hololive member that should be added
     */
    public void addMember(HololiveMemberDTO hololiveMember){
        repository.addMember(modelMapper.map(hololiveMember, HololiveMember.class));
    }

    /**
     * Gets all videos of a hololive member.
     * @param memberId the ID of the hololive member whose videos should be retrieved
     * @return a list containing all videos of the hololive member
     */
    public List<VideoDTO> getMemberVideos(Long memberId){
        return ModelMapperUtil.mapAll(repository.getMemberById(memberId).getVideos(), VideoDTO.class, modelMapper);
    }
}
