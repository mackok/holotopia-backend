package unit.handler;

import nhl.stenden.handler.manifest.EmbedPageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmbedPageHandlerTest {

    private final EmbedPageHandler embedPageHandler = new EmbedPageHandler();

    /**
     * Tests whether the correct URL is returned when a valid video ID is given.
     */
    @Test
    void getSourceURLValidTest(){
        String videoId = "EhQlnDw2c_I";
        String playerSourceURL = "/s/player/bfb74eaf/player_ias.vflset/.{5}/base.js";
        Assertions.assertTrue(embedPageHandler.getSourceURL(videoId).matches(playerSourceURL));
    }

    //Because the API always returns a player url, I couldn't test invalid video ID's
}
