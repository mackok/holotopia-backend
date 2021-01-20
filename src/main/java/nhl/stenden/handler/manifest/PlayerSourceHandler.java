package nhl.stenden.handler.manifest;

import nhl.stenden.cipher.CipherOperation;
import nhl.stenden.cipher.ReverseOperation;
import nhl.stenden.cipher.SliceOperation;
import nhl.stenden.cipher.SwapOperation;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

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

    //Todo: Check whether this is needed.
    public List<CipherOperation> getCipherOperation(String playerSource){
        List<CipherOperation> operations = new ArrayList<>();
        String page = getPlayerSourcePage(playerSource);

        Pattern pattern = Pattern.compile("\\b(?<sig>[a-zA-Z0-9$]{2})\\s*=\\s*function\\(\\s*a\\s*\\)\\s*\\{\\s*a\\s*=\\s*a\\.split\\(\\s*\"\"\\s*\\)");
        String cipherFunc = "";
        Matcher matcher = pattern.matcher(page);
        while(matcher.find()){
            cipherFunc = matcher.group("sig");
        }

        pattern = Pattern.compile(cipherFunc + "=function\\(\\w+\\)\\{(.*?)\\}");
        String cipherFuncBody = "";
        matcher = pattern.matcher(page);
        while(matcher.find()){
            cipherFuncBody = matcher.group(1);
        }

        String[] cipherFuncLines = cipherFuncBody.split(";");
        for(String line : cipherFuncLines){
            pattern = Pattern.compile("\\w+\\.(\\w+)\\(");
            String funcName = "";
            matcher = pattern.matcher(line);
            while (matcher.find()){
                funcName = matcher.group(1);
            }

            pattern = Pattern.compile(funcName + ":\\bfunction\\b\\(\\w+\\)");
            matcher = pattern.matcher(page);
            if(matcher.find()){
                pattern = Pattern.compile("\\(\\w+,(\\d+)\\)");
                matcher = pattern.matcher(line);
                while (matcher.find()){
                    operations.add(new SwapOperation(Integer.parseInt(matcher.group(1))));
                }
            }

            pattern = Pattern.compile(funcName + ":\\bfunction\\b\\([a],b\\).(\\breturn\\b)?.?\\w+\\.");
            matcher = pattern.matcher(page);
            if(matcher.find()){
                pattern = Pattern.compile("\\(\\w+,(\\d+)\\)");
                matcher = pattern.matcher(line);
                while (matcher.find()){
                    operations.add(new SliceOperation(Integer.parseInt(matcher.group(1))));
                }
            }

            pattern = Pattern.compile(funcName + ":\\bfunction\\b\\(\\w+\\,\\w\\).\\bvar\\b.\\bc=a\\b");
            matcher = pattern.matcher(page);
            if(matcher.find()){
                operations.add(new ReverseOperation());
            }
        }

        return operations;
    }
}
