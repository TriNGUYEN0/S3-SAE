package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PnjVue {
    private Pane paneMap;
    private IntegerProperty pnjX;
    private IntegerProperty pnjY;
    private Image pnjSprite;
    private ImageView pnjSpriteView;
    public PnjVue(Pane paneMap, IntegerProperty pnjX, IntegerProperty pnjY) {
        this.paneMap = paneMap;
        this.pnjX = pnjX;
        this.pnjY = pnjY;
        this.pnjSprite =new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/eileen.png").toString());;
    }

    public void creerSpritePnj(){
        pnjSpriteView = new ImageView(pnjSprite);
        pnjSpriteView.translateXProperty().bind(this.pnjX);
        pnjSpriteView.translateYProperty().bind(this.pnjY);
    }
    public void initialiserPnj(ImageView imageView, Pane pane){
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        pane.getChildren().add(imageView);
    }

    public ImageView getPnjSpriteView() {
        return pnjSpriteView;
    }
}
