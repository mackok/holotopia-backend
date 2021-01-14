package nhl.stenden.handler.manifest;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that handles the process of getting a player source URL using a YouTube video ID.
 */
@Component
public class EmbedPageHandler {

    /**
     * Gets the player source URL from the related embed page.
     * @param videoId the YouTube ID of the video whose player source URL should be extracted
     * @return the player source URL that is used in the video
     */
    public String getSourceURL(String videoId){
        String page = getEmbedPage(videoId);
        Pattern pattern = Pattern.compile("(?<=\"jsUrl\":\")(.+?)(?=\",)");
        Matcher matcher = pattern.matcher(page);
        String output = "";

        while(matcher.find()){
            output = matcher.group();
        }
        return output;
    }

    /**
     * Gets the related embed page from a video using it's YouTube ID
     * @param videoId the YouTube ID of the video whose embed page should be retrieved
     * @return a String containing the source code of the embed page
     */
    private String getEmbedPage(String videoId){
        StringBuilder embedPage = new StringBuilder();

        try{
            String urlString = "https://www.youtube.com/embed/" + videoId;
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
