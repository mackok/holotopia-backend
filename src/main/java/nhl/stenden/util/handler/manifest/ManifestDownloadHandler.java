package nhl.stenden.util.handler.manifest;

import nhl.stenden.model.Video;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ManifestDownloadHandler {

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
