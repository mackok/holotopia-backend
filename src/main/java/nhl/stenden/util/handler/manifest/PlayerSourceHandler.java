package nhl.stenden.util.handler.manifest;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PlayerSourceHandler {

    public String getSts(String playerSource){
        String page = getPlayerSourcePage(playerSource);
        Pattern pattern = Pattern.compile("(?<=sts:)\\d{5}");
        Matcher matcher = pattern.matcher(page);
        String output = "";

        while(matcher.find()){
            output = matcher.group();
        }
        return output;
    }

    private String getPlayerSourcePage(String playerSource){
        StringBuilder embedPage = new StringBuilder();

        try{
            String urlString = "https://www.youtube.com/" + playerSource;
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
