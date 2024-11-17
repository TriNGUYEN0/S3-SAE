package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;

public class EnnemiVue {
    private Pane paneMap;
    public static String[] framesGauche;
    public static String[] framesDroite;
    public static String[] framesHaut;
    public static String[] framesBas;
    public AnimatedEnnemiSprite animatedEnnemiSprite;
    private ImageView ennemiSprite;
    private IntegerProperty ennemiXProperty;
    private IntegerProperty ennemiYProperty;

    public EnnemiVue(IntegerProperty ennemiXProperty, IntegerProperty ennemiYProperty, Pane paneMap, ImageView v){
        framesGauche = new String[]{getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_left_1.png").toExternalForm(), getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_left_2.png").toExternalForm()};
        framesDroite = new String[]{getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_right_1.png").toExternalForm(), getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_right_2.png").toExternalForm()};
        framesHaut = new String[]{getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_up_1.png").toExternalForm(), getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_up_2.png").toExternalForm()};
        framesBas = new String[]{getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_down_1.png").toExternalForm(), getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_down_2.png").toExternalForm()};
        this.ennemiSprite = v;
        this.animatedEnnemiSprite = new AnimatedEnnemiSprite(framesDroite, v);
        this.paneMap = paneMap;
        this.ennemiXProperty = ennemiXProperty;
        this.ennemiYProperty = ennemiYProperty;
    }

    public void creerSpriteEnnemi(){
        ennemiSprite = this.animatedEnnemiSprite.getImageView();
        ennemiSprite.translateXProperty().bind(this.ennemiXProperty);
        ennemiSprite.translateYProperty().bind(this.ennemiYProperty);
        this.animatedEnnemiSprite.start();
    }

    public void initialiserEnnemi(ImageView sprite, Pane paneMap) {
        if (!paneMap.getChildren().contains(sprite)) { // Chỉ thêm nếu chưa tồn tại
            paneMap.getChildren().add(sprite);
            System.out.println("Sprite của Ennemi đã được thêm vào paneMap.");
        } else {
            System.err.println("Sprite của Ennemi đã tồn tại trong paneMap.");
        }
    }



    public void animerEnnemi(AnimatedEnnemiSprite animationTimer, Direction direction){
        if (direction == Direction.OUEST) {
            animationTimer.updateFrames(framesGauche);                        }
        else if (direction == Direction.EST) {
            animationTimer.updateFrames(framesDroite);                        }
        else if (direction == Direction.NORD) {
            animationTimer.updateFrames(framesHaut);                        }
        else if (direction == Direction.SUD) {
            animationTimer.updateFrames(framesBas);                        }
    }
}
