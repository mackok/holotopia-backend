package nhl.stenden.util.handler.manifest;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmbedPageHandler {

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
