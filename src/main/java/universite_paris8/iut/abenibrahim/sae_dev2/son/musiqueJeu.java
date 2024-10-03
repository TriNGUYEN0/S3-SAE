package universite_paris8.iut.abenibrahim.sae_dev2.son;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
public class musiqueJeu {
    private static MediaPlayer mediaPlayer;


    public void lancerMusique1(){
        String audioFile = "src/main/resources/universite_paris8/iut/abenibrahim/sae_dev2/son2.mp3";
        String absolutePath = Paths.get(audioFile).toAbsolutePath().toUri().toString();
        Media media = new Media(absolutePath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        });

        mediaPlayer.play();
    }

    public static void stopGameMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

}


