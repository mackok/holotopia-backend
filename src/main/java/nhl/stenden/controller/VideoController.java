package nhl.stenden.controller;

import nhl.stenden.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles requests related to videos.
 */
@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService service;

    @Autowired
    public VideoController(VideoService service){
        this.service = service;
    }

    /**
     * Get request that retrieves the video url of a video.
     * @param id the youtube ID of a video
     * @return a String containing the video url of the video
     */
    @GetMapping("/{id}/url")
    public String getVideoUrl(@PathVariable String id){
        return service.getVideoUrl(id);
    }
}
