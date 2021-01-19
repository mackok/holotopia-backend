package nhl.stenden.service;

import nhl.stenden.handler.manifest.ManifestHandler;
import nhl.stenden.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that handles the business logic of requests related to videos.
 */
@Service
public class VideoService {

    private final VideoRepository repository;
    private final ManifestHandler manifestHandler;

    @Autowired
    public VideoService(VideoRepository repository, ManifestHandler manifestHandler){
        this.repository = repository;
        this.manifestHandler = manifestHandler;
    }

    /**
     * Gets a video url of a video.
     * @param youtubeVideoId the youtube ID of the video
     * @return a String containing the video url of the video
     */
    public String getVideoUrl(String youtubeVideoId){
        return manifestHandler.getVideoUrl(repository.getVideoByYtId(youtubeVideoId));
    }
}
