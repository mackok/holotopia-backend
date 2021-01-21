package nhl.stenden.handler;

import nhl.stenden.handler.manifest.EmbedPageHandler;
import nhl.stenden.handler.manifest.ManifestHandler;
import nhl.stenden.handler.manifest.PlayerSourceHandler;
import nhl.stenden.model.Video;
import nhl.stenden.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoInfoHandler {

    private final VideoRepository videoRepository;
    private final EmbedPageHandler embedPageHandler;
    private final PlayerSourceHandler playerSourceHandler;

    @Autowired
    public VideoInfoHandler(VideoRepository videoRepository, EmbedPageHandler embedPageHandler,
                           PlayerSourceHandler playerSourceHandler){
        this.videoRepository = videoRepository;
        this.embedPageHandler = embedPageHandler;
        this.playerSourceHandler = playerSourceHandler;
    }

    public void updateInfo(){
        List<Video> videos = videoRepository.getAllVideos();

        for(Video video : videos){
            if(video.getPlayerSource() == null){
                video.setPlayerSource(embedPageHandler.getSourceURL(video.getYoutubeCode()));
                videoRepository.updateVideo(video);
            }

            if(video.getSts() == null){
                video.setSts(playerSourceHandler.getSts(video.getPlayerSource()));
                videoRepository.updateVideo(video);
            }
        }
    }
}
