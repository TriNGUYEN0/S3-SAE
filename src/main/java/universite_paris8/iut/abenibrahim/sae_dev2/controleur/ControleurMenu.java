package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import universite_paris8.iut.abenibrahim.sae_dev2.Main;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.SaveData;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;


public class ControleurMenu {
    public VBox vbox;
    @FXML
    private Button playButton;
    @FXML
    private MediaView mediaView;

    @FXML
    private Button continuerButton;
    @FXML
    private Button toucheButton;

    private ControleurToucheMenu controleurToucheMenu;

    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer videoMediaPlayer;

    public void initialize() {

        controleurToucheMenu = new ControleurToucheMenu(this);
        String audioFile = "src/main/resources/universite_paris8/iut/abenibrahim/sae_dev2/son.mp3";
        String audio = "src/main/resources/universite_paris8/iut/abenibrahim/sae_dev2/son2.mp3";// Chemin relatif vers le fichier audio
        String absolutePath = Paths.get(audioFile).toAbsolutePath().toUri().toString();
        String absolutePath2 = Paths.get(audio).toAbsolutePath().toUri().toString();
        Media media = new Media(absolutePath);
        Media media2 = new Media(absolutePath2);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer2 = new MediaPlayer(media2);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer2.setVolume(0.3);
    }

    @FXML
    private void handlePlayButtonAction() throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        vbox.setVisible(false);


        String videoFile = "src/main/resources/universite_paris8/iut/abenibrahim/sae_dev2/video1.mp4";
        String videoPath = Paths.get(videoFile).toAbsolutePath().toUri().toString();
        Media videoMedia = new Media(videoPath);
        videoMediaPlayer = new MediaPlayer(videoMedia);
        mediaView.setMediaPlayer(videoMediaPlayer);
        videoMediaPlayer.setVolume(0.5);
        // Définir le début et la fin de la partie de la vidéo que vous souhaitez lire
        Duration startTime = Duration.seconds(63);  // Commence à 10 secondes
        Duration endTime = Duration.seconds(101);    // Se termine à 30 secondes
        videoMediaPlayer.setStartTime(startTime);
        videoMediaPlayer.setStopTime(endTime);
        videoMediaPlayer.play();
        Scene scene = playButton.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, controleurToucheMenu);
        videoMediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer2.play();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/lafoudre.fxml"));
                Parent root = loader.load();
                Scene newScene = new Scene(root);
                Stage stage = (Stage) playButton.getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @FXML
    private void handlecontinuerButtonAction() throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        loadGame();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/lafoudre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) continuerButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadGame() {
        try {
            FileInputStream fis = new FileInputStream("savegame.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SaveData saveData = (SaveData) ois.readObject();
            ois.close();

            // Restaurer les données de l'environnement à partir de saveData
            Environnement env = new Environnement();
            Joueur guts = env.getGuts();
            guts.setX(saveData.getGutsX());
            guts.setY(saveData.getGutsY());
            guts.setPv(saveData.getGutsPv());

            Ennemi ennemi = env.getEnnemi();
            ennemi.setX(saveData.getEnnemiX());
            ennemi.setY(saveData.getEnnemiY());
            ennemi.setPv(saveData.getEnnemiPv());

            env.getMap().setTab(saveData.getMapData());
            env.getMap().setTab2(saveData.getMapData2());

            // Stocker l'environnement chargé pour l'utiliser dans la scène de jeu
            Main.setEnvironnement(env);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void skipAnimation() {
        if (videoMediaPlayer != null) {
            videoMediaPlayer.stop();
            mediaPlayer2.play();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/lafoudre.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) playButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleAideButton(ActionEvent actionEvent)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/touche.fxml"));
        Parent root = loader.load();
        Stage aideStage = new Stage();
        aideStage.setTitle("Aide");
        aideStage.setScene(new Scene(root));
        aideStage.show();
    }
}