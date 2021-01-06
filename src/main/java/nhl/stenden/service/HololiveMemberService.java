package nhl.stenden.service;

import nhl.stenden.dto.HololiveMemberDTO;
import nhl.stenden.model.HololiveMember;
import nhl.stenden.repository.HololiveMemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HololiveMemberService {

    private final HololiveMemberRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public HololiveMemberService(HololiveMemberRepository repository, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public HololiveMemberDTO getById(Long id){
        return modelMapper.map(repository.findMemberById(id), HololiveMemberDTO.class);
    }

    public void addMember(HololiveMemberDTO hololiveMember){
        repository.addMember(modelMapper.map(hololiveMember, HololiveMember.class));
    }
}
