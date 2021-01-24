package unit.handler;

import nhl.stenden.handler.manifest.PlayerSourceHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerSourceHandlerTest {

    private final PlayerSourceHandler playerSourceHandler = new PlayerSourceHandler();

    /**
     * Tests whether the correct sts is returned when a valid player source is given.
     */
    @Test
    void getStsValidTest(){
        String playerSource = "/s/player/9f996d3e/player_ias.vflset/nl_NL/base.js";
        Assertions.assertEquals("18634", playerSourceHandler.getSts(playerSource));
    }

    /**
     * Tests whether an empty String is returned when an invalid player source is given.
     */
    @Test
    void getStsInvalidPlayerSourceTest(){
        String playerSource = "test";
        Assertions.assertEquals("", playerSourceHandler.getSts(playerSource));
    }
}
