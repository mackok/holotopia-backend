package nhl.stenden.util.handler.request;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static nhl.stenden.util.constants.APIConstants.*;

@NoArgsConstructor
public class YoutubeRequestHandler {

    private static final String APP_NAME = "Holotopia";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

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
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    private YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APP_NAME)
                .build();
    }

    private PlaylistItemListResponse getPageVideos(String uploadsId, String pageToken){
        PlaylistItemListResponse response = null;
        try {
            YouTube youtubeService = getService();
            // Define and execute the API request
            YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                    .list("snippet, contentDetails");
            response = request.setKey(YOUTUBE_KEY)
                    .setMaxResults(50L)
                    .setPageToken(pageToken)
                    .setPlaylistId(uploadsId)
                    .execute();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
