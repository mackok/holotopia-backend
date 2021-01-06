package nhl.stenden.controller;

import nhl.stenden.dto.HololiveMemberDTO;
import nhl.stenden.service.HololiveMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/hololive-members")
public class HololiveMemberController {

    private final HololiveMemberService service;

    @Autowired
    public HololiveMemberController(HololiveMemberService service){
        this.service = service;
    }

    @PostMapping
    public void addMember(@RequestBody @Valid HololiveMemberDTO hololiveMember){
        service.addMember(hololiveMember);
    }
}
