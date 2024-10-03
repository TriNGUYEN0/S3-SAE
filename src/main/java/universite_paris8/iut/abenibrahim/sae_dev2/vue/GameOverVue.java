package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class GameOverVue extends VBox {
    private Pane paneMap;
    private StackPane gameOverStackPane;
    private ImageView imageView;

    public GameOverVue(Pane paneMap) {
        this.paneMap = paneMap;
        gameOverStackPane = new StackPane();
        imageView = new ImageView(new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gameover.png").toExternalForm()));
        imageView.setFitWidth(800);
        imageView.setFitHeight(300);
        gameOverStackPane.getChildren().add(imageView);
        gameOverStackPane.setLayoutX(10);
        gameOverStackPane.setLayoutY(10);
        paneMap.getChildren().add(gameOverStackPane);
    }

    public Node getGameOverStackPane() {
        return this.gameOverStackPane;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void updatePosition(double joueurX, double joueurY) {
        gameOverStackPane.setLayoutX(joueurX - (gameOverStackPane.getWidth() / 2));
        gameOverStackPane.setLayoutY(joueurY - (gameOverStackPane.getHeight() / 2));
    }
}
