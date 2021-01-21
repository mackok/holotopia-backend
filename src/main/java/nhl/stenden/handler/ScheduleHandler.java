package nhl.stenden.handler;

import nhl.stenden.handler.api.YoutubeAPIHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class that handles methods that should be scheduled.
 */
@Component
public class ScheduleHandler {

    private final YoutubeAPIHandler youtubeAPIHandler;
    private final VideoInfoHandler videoInfoHandler;

    public ScheduleHandler(YoutubeAPIHandler youtubeAPIHandler, VideoInfoHandler videoInfoHandler){
        this.youtubeAPIHandler = youtubeAPIHandler;
        this.videoInfoHandler = videoInfoHandler;
    }

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 15)
    public void updateInfo(){
        System.out.println("Updating hololive videos in the database...");
        youtubeAPIHandler.updateInfo();
        System.out.println("Updating of hololive videos complete");

        System.out.println("Updating video info in the database...");
        videoInfoHandler.updateInfo();
        System.out.println("Updating of video info complete");
    }
}
