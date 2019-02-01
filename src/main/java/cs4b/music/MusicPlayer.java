package cs4b.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MusicPlayer {
    private static MusicPlayer instance;
    public static MusicPlayer getInstance() {
        if (instance == null)
            instance = new MusicPlayer();
        return instance;
    }
    MediaPlayer mediaPlayer;
    private MusicPlayer() {
        String path = "src/main/resources/music/01 A Night Of Dizzy Spells.mp3";
        Media hit = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }

    public void toggle() {

        if(mediaPlayer.isMute()) {
            // next song
            mediaPlayer.setMute(false);
        }
        else {
            mediaPlayer.setMute(true);
        }
    }


}
