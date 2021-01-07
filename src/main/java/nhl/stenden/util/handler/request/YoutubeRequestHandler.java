package nhl.stenden.util.handler.request;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import nhl.stenden.model.HololiveMember;
import nhl.stenden.model.Video;
import nhl.stenden.repository.HololiveMemberRepository;
import nhl.stenden.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static nhl.stenden.util.constants.APIConstants.*;

@Component
public class YoutubeRequestHandler {

    private static final String APP_NAME = "Holotopia";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final HololiveMemberRepository memberRepository;
    private final VideoRepository videoRepository;
    private YouTube youtubeService;

    @Autowired
    public YoutubeRequestHandler(HololiveMemberRepository memberRepository, VideoRepository videoRepository){
        this.memberRepository = memberRepository;
        this.videoRepository = videoRepository;

        try {
            setService();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    //Todo: change x in comment to amount of minutes
    /**
     * Gets information from the YouTube API every x minutes.
     */
    private void startTask(){
        Runnable task = this::updateInfo;
        //Todo: change delay after testing
        int delay = 15;
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(task, 0, delay, TimeUnit.MINUTES);
    }

    /**
     * Updates the database with necessary info from the YouTube API.
     */
    private void updateInfo(){
        Thread thread = new Thread(() -> {
            for(HololiveMember member : memberRepository.getAllMembers()){
                Set<Video> storedVideos = member.getVideos();

                if(storedVideos.isEmpty()){
                    List<PlaylistItem> videoItems = getAllVideos(member.getUploads());
                    List<Video> videos = convertPlaylistItems(videoItems, member);
                    videoRepository.addVideos(videos);
                }
                else{
                    storeLatestVideos(member);
                }
            }
            System.out.println("Updating of hololive videos complete");
        });
        thread.start();
    }

    /**
     * Checks whether the 10 latest video are already in the database. If not, adds the video to the database.
     * @param member the hololive member the videos belong to
     */
    private void storeLatestVideos(HololiveMember member){
        List<PlaylistItem> latestVideos = getLatestVideos(member.getUploads()).getItems();

        for (PlaylistItem video : latestVideos) {
            boolean isStored = false;
            String videoId = video.getContentDetails().getVideoId();

            for (Video storedVideo : member.getVideos()) {
                if (storedVideo.getYoutubeCode().equals(videoId)) {
                    isStored = true;
                    break;
                }
            }

            if(!isStored){
                videoRepository.addVideo(convertPlaylistItem(video, member));
            }
        }
    }

    /**
     * Gets all video ids from a YouTube channel.
     *
     * @param uploadsId the id of the channel uploads
     * @return A list of all video ids
     */
    public List<String> getAllVideoIds(String uploadsId){
        List<String> videoIds = new ArrayList<>();
        List<PlaylistItem> videos = getAllVideos(uploadsId);

        for (PlaylistItem playlistItem : videos) {
            videoIds.add(playlistItem.getContentDetails().getVideoId());
        }

        return videoIds;
    }

    /**
     * Gets the information of all videos from a YouTube channel.
     *
     * @param uploadsId the id of the channel uploads
     * @return A list of all video ids
     */
    private List<PlaylistItem> getAllVideos(String uploadsId) {
        List<PlaylistItem> videos = new ArrayList<>();
        boolean go = true;
        boolean first = true;
        PlaylistItemListResponse response = null;

        while(go){
            if(first){
                response = getPageVideos(uploadsId, "");
                if(response.getItems() != null){
                    videos.addAll(response.getItems());
                }
                first = false;
            }
            else{
                String nextPageToken = response.getNextPageToken();
                if(nextPageToken != null){
                    response = getPageVideos(uploadsId, nextPageToken);
                    if(response.getItems() != null){
                        videos.addAll(response.getItems());
                    }
                }
                else{
                    go = false;
                }
            }
        }

        return videos;
    }

    /**
     * Builds an authorized API client service.
     *
     * @throws GeneralSecurityException, IOException
     */
    private void setService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        youtubeService = new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APP_NAME)
                .build();
    }

    /**
     * Gets the information of all videos from a page of a channel.
     * @param uploadsId the id of the channel uploads
     * @param pageToken the token of the page
     * @return a list of all video information
     */
    private PlaylistItemListResponse getPageVideos(String uploadsId, String pageToken){
        PlaylistItemListResponse response = null;
        try {
            // Define and execute the API request
            YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                    .list("snippet, contentDetails");
            response = request.setKey(YOUTUBE_KEY)
                    .setMaxResults(50L)
                    .setPageToken(pageToken)
                    .setPlaylistId(uploadsId)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Gets the information of the 10 latest videos of a channel.
     * @param uploadsId the id of the channel uploads
     * @return a list of all video information
     */
    private PlaylistItemListResponse getLatestVideos(String uploadsId){
        PlaylistItemListResponse response = null;
        try {
            // Define and execute the API request
            YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                    .list("snippet, contentDetails");
            response = request.setKey(YOUTUBE_KEY)
                    .setMaxResults(10L)
                    .setPlaylistId(uploadsId)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Converts a list of PlayListItem objects to a list of Video objects.
     * @param videoItems the list that should be converted
     * @param member the hololive member the list belongs to
     * @return the converted list of Video objects
     */
    private List<Video> convertPlaylistItems(List<PlaylistItem> videoItems, HololiveMember member){
        List<Video> videos = new ArrayList<>();

        for(PlaylistItem videoItem : videoItems){
            videos.add(convertPlaylistItem(videoItem, member));
        }

        return videos;
    }

    /**
     * Converts a PlayListItem object to a Video object.
     * @param videoItem the item that should be converted
     * @param member the hololive member the list belongs to
     * @return the converted Video object
     */
    private Video convertPlaylistItem(PlaylistItem videoItem, HololiveMember member){
        Video video = new Video();
        video.setYoutubeCode(videoItem.getContentDetails().getVideoId());
        video.setMember(member);
        return video;
    }
}
