package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AideControleur {
    @FXML
    private Button fermerButton;

    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void fermerAide() {
        // Ferme la fenêtre actuelle (l'aide)
        Stage stage = (Stage) fermerButton.getScene().getWindow();
        stage.close();

        // Retourne à la scène principale
        if (mainStage != null) {
            mainStage.show();
        }
    }
}
