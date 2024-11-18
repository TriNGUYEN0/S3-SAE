package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;

public class EnnemiVue {
    private final Pane paneMap;
    private final Ennemi ennemi;
    private final ImageView ennemiSprite;
    private final AnimatedEnnemiSprite animatedEnnemiSprite;

    private static final String[] framesGauche;
    private static final String[] framesDroite;
    private static final String[] framesHaut;
    private static final String[] framesBas;

    static {
        framesGauche = new String[]{
                "/universite_paris8/iut/abenibrahim/sae_dev2/boy_left_1.png",
                "/universite_paris8/iut/abenibrahim/sae_dev2/boy_left_2.png"
        };
        framesDroite = new String[]{
                "/universite_paris8/iut/abenibrahim/sae_dev2/boy_right_1.png",
                "/universite_paris8/iut/abenibrahim/sae_dev2/boy_right_2.png"
        };
        framesHaut = new String[]{
                "/universite_paris8/iut/abenibrahim/sae_dev2/boy_up_1.png",
                "/universite_paris8/iut/abenibrahim/sae_dev2/boy_up_2.png"
        };
        framesBas = new String[]{
                "/universite_paris8/iut/abenibrahim/sae_dev2/boy_down_1.png",
                "/universite_paris8/iut/abenibrahim/sae_dev2/boy_down_2.png"
        };
    }

    public EnnemiVue(Ennemi ennemi, Pane paneMap) {
        this.ennemi = ennemi;
        this.paneMap = paneMap;

        // Khởi tạo ImageView cho Ennemi
        Image initialImage = new Image(getClass().getResource(framesDroite[0]).toExternalForm());
        this.ennemiSprite = new ImageView(initialImage);
        this.ennemiSprite.setFitWidth(50);
        this.ennemiSprite.setFitHeight(50);

        // Ràng buộc vị trí của sprite với vị trí của Ennemi
        this.ennemiSprite.translateXProperty().bind(this.ennemi.xProperty());
        this.ennemiSprite.translateYProperty().bind(this.ennemi.yProperty());

        // Khởi tạo hoạt ảnh
        this.animatedEnnemiSprite = new AnimatedEnnemiSprite(framesDroite, ennemiSprite);
    }

    public void creerSpriteEnnemi() {
        if (!paneMap.getChildren().contains(ennemiSprite)) {
            paneMap.getChildren().add(ennemiSprite);
            System.out.println("Sprite của Ennemi đã được thêm vào paneMap.");
        } else {
            System.err.println("Sprite của Ennemi đã tồn tại trong paneMap.");
        }
        // Bắt đầu hoạt ảnh
        animatedEnnemiSprite.start();
    }

    public void animerEnnemi(Direction direction) {
        switch (direction) {
            case OUEST -> animatedEnnemiSprite.updateFrames(framesGauche);
            case EST -> animatedEnnemiSprite.updateFrames(framesDroite);
            case NORD -> animatedEnnemiSprite.updateFrames(framesHaut);
            case SUD -> animatedEnnemiSprite.updateFrames(framesBas);
        }
    }

    public ImageView getEnnemiSprite() {
        return ennemiSprite;
    }

    public Ennemi getEnnemi() {
        return ennemi;
    }
}

