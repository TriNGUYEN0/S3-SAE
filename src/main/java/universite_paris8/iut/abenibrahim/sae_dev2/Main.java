package universite_paris8.iut.abenibrahim.sae_dev2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("lafoudre.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),750,750);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Pane root = (Pane) scene.getRoot();
        root.requestFocus();
        stage.setTitle("LA FOUDRE");
        stage.setScene(scene);
        stage.show();
    }
}