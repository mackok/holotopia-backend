package nhl.stenden.controller;

import nhl.stenden.dto.HololiveMemberDTO;
import nhl.stenden.dto.VideoDTO;
import nhl.stenden.service.HololiveMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller that handles requests related to hololive members.
 */
@RestController
@RequestMapping("/hololive-members")
public class HololiveMemberController {

    private final HololiveMemberService service;

    @Autowired
    public HololiveMemberController(HololiveMemberService service){
        this.service = service;
    }

    /**
     * Post request that adds a hololive member to the database.
     * @param hololiveMember the hololive member that should be added to the database
     */
    @PostMapping
    public void addMember(@RequestBody @Valid HololiveMemberDTO hololiveMember){
        service.addMember(hololiveMember);
    }

    /**
     * Get request that retrieves all videos from a hololive member.
     * @param id the ID of the hololive member whose videos should be retrieved
     * @return a list containing all videos from the hololive member
     */
    @GetMapping("/{id}/videos")
    public List<VideoDTO> getMemberVideos(@PathVariable Long id){
        return service.getMemberVideos(id);
    }
}
