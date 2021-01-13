package nhl.stenden.util.handler.manifest;

import nhl.stenden.model.Video;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseManifestHandler {

    public String parseManifest(String manifest){
        Pattern pattern = Pattern.compile("(?<=sts:)\\d{5}");
        return "";
    }
}
