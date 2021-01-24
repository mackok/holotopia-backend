package unit.handler;

import nhl.stenden.handler.manifest.ManifestHandler;
import nhl.stenden.model.Video;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManifestHandlerTest {

    private final ManifestHandler manifestHandler = new ManifestHandler();
    private Video video;

    @BeforeEach
    void init(){
        video = new Video();
    }

    /**
     * Tests whether the correct video URL is returned when a valid youtube code and sts are given.
     */
    @Test
    void getVideoUrlValidTest(){
        video.setYoutubeCode("huOiq5otFaY");
        video.setSts("18634");
        String videoUrl = "https://r3---sn-5hne.+?(?=\\.)\\.googlevideo\\.com/videoplayback\\?expire=\\d+?(?=&)&ei=.+?(?=&)" +
                "&ip=.+?(?=&)&id=.+?(?=&)&itag=22&source=youtube&requiressl=yes&mh=ws&mm=.+?(?=&)&mn=.+?(?=&)" +
                "&ms=.+?(?=&)&mv=.+?(?=&pl)&pl=.+?(?=&)&initcwndbps=\\d+?(?=&)&vprv=1" +
                "&mime=video%2Fmp4&ns=.+?(?=&ratebypass)&ratebypass=yes&dur=11308\\.048&lmt=1610089549666869" +
                "&mt=.+?(?=&)&fvip=3(&beids=\\d+)?&c=WEB&txp=7316222&n=.+?(?=&)&sparams=.+?(?=&)&sig=.+?(?=&)" +
                "&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=.+";
        Assertions.assertTrue(manifestHandler.getVideoUrl(video).matches(videoUrl));
    }

    /**
     * Tests whether there is no video URL returned when an invalid youtube code is given.
     */
    @Test
    void getVideoUrlInvalidVideoIdTest(){
        video.setYoutubeCode("O_o");
        video.setSts("18634");
        Assertions.assertEquals("", manifestHandler.getVideoUrl(video));
    }

    //Video URL with invalid sts can't be tested since it will sometimes (but not always) return a valid video URL
}
