package nhl.stenden.util.handler.manifest;

import nhl.stenden.model.Video;
import nhl.stenden.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManifestHandler {

    private final VideoRepository videoRepository;
    private final EmbedPageHandler embedPageHandler;
    private final PlayerSourceHandler playerSourceHandler;
    private final ManifestDownloadHandler manifestDownloadHandler;

    @Autowired
    public ManifestHandler(VideoRepository videoRepository, EmbedPageHandler embedPageHandler,
                           PlayerSourceHandler playerSourceHandler, ManifestDownloadHandler manifestDownloadHandler){
        this.videoRepository = videoRepository;
        this.embedPageHandler = embedPageHandler;
        this.playerSourceHandler = playerSourceHandler;
        this.manifestDownloadHandler = manifestDownloadHandler;
    }

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 15)
    public void getAllEmbedPages(){
        List<Video> videos = videoRepository.getAllVideos();

        for(Video video : videos){
            if(video.getPlayerSource() == null){
                video.setPlayerSource(embedPageHandler.getSourceURL(video.getYoutubeCode()));
                videoRepository.updateVideo(video);
            }
            else if(video.getSts() == null){
                video.setSts(playerSourceHandler.getSts(video.getPlayerSource()));
                videoRepository.updateVideo(video);
            }
            else {
                //manifestDownloadHandler.getManifest(video);
            }
        }
    }
}
