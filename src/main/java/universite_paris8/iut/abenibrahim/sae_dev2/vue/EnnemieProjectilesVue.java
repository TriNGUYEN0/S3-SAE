package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EnnemieProjectilesVue {

        private Pane paneMap;
        private IntegerProperty EnnemieX;
        private IntegerProperty EnnemieY;
        private Image EnnemieSprite;
        private ImageView EnnemieSpriteView;
        public EnnemieProjectilesVue(Pane paneMap, IntegerProperty EnnemieX, IntegerProperty EnnemieY) {
            this.paneMap = paneMap;
            this.EnnemieX = EnnemieX;
            this.EnnemieY = EnnemieY;
            this.EnnemieSprite =new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/huntsman_rifle.png").toString());;
        }

        public void creerSpritePnj(){
            EnnemieSpriteView = new ImageView(EnnemieSprite);
            EnnemieSpriteView.translateXProperty().bind(this.EnnemieX);
            EnnemieSpriteView.translateYProperty().bind(this.EnnemieY);
        }
        public void initialiserEnnemieProjectiles(ImageView imageView, Pane pane){
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            pane.getChildren().add(imageView);
        }

        public ImageView getEnnemieSpriteView() {
            return EnnemieSpriteView;
        }

}
