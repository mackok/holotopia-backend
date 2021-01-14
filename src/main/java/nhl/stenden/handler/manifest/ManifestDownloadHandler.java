package nhl.stenden.handler.manifest;

import nhl.stenden.model.Video;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that handles retrieving and decrypting the encrypted manifest file.
 */
@Component
public class ManifestDownloadHandler {

    //Todo: Decrypt manifest.
    public String getManifest(Video video){
        String page = getManifestFile(video);
        Pattern pattern = Pattern.compile("(?<=sts:)\\d{5}");
        Matcher matcher = pattern.matcher(page);
        String output = "";

        while(matcher.find()){
            output = matcher.group();
        }
        return output;
    }

    /**
     * Gets the encrypted manifest from a video.
     * @param video the video whose manifest should be retrieved
     * @return a String containing the encrypted manifest
     */
    private String getManifestFile (Video video){
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
