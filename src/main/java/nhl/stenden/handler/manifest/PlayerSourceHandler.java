package nhl.stenden.handler.manifest;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that handles retrieving and analyzing the YouTube player source page.
 */
@Component
public class PlayerSourceHandler {

    /**
     * Extracts the sts from the source code of a YouTube player source page.
     * @param playerSource the URL of the player source page
     * @return a String containing the sts
     */
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

    /**
     * Gets the source code from a YouTube player source page URL.
     * @param playerSource the URL of the player source page
     * @return a String containing the source code of the player source page
     */
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
