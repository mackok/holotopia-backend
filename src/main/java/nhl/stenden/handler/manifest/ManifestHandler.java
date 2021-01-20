package nhl.stenden.handler.manifest;

import nhl.stenden.model.Video;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that handles the process of getting a manifest from a video ID.
 */
@Component
public class ManifestHandler {

    /**
     * Extracts the video url using the manifest of a video.
     * @param video the video whose video url should be retrieved
     * @return a String containing the video url
     */
    public String getVideoUrl(Video video){
        String manifest = getManifest(video);
        Pattern pattern = Pattern.compile("(?<=\"itag\":22,\"url\":\").+?(?=\")");
        Matcher matcher = pattern.matcher(manifest);
        String output = "";

        while(matcher.find()){
            output = matcher.group();
        }
        return output;
    }

    /**
     * Gets the decrypted manifest from a video.
     * @param video the video whose manifest should be retrieved
     * @return a String containing the decrypted manifest
     */
    private String getManifest(Video video){
        String output = getEncryptedManifest(video);
        output = URLDecoder.decode(output, StandardCharsets.UTF_8);
        output = output.replace("\\u0026", "&");
        return output;
    }

    /**
     * Gets the encrypted metadata from a video.
     * @param video the video whose metadata should be retrieved
     * @return a String containing the encrypted metadata
     */
    private String getEncryptedManifest(Video video){
        StringBuilder embedPage = new StringBuilder();

        try{
            String urlString = "https://www.youtube.com/get_video_info?video_id=" + video.getYoutubeCode() + "&sts=" +
                    video.getSts() + "&hl=en";
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while((line = reader.readLine()) != null){
                embedPage.append(line);
            }

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        return embedPage.toString();
    }
}
